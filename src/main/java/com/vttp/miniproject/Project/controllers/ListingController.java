package com.vttp.miniproject.Project.controllers;

import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Listing;
import com.vttp.miniproject.Project.services.CommentService;
import com.vttp.miniproject.Project.services.GmapService;
import com.vttp.miniproject.Project.services.TIHSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(path = "/auth/listing/")
@Controller
public class ListingController {
    Logger logger = Logger.getLogger(ListingController.class.getName());

    @Autowired
    TIHSearchService searchService;

    @Autowired
    GmapService gmapService;

    @Autowired
    CommentService commentService;

    @GetMapping(path = "/{uuid}")
    public ModelAndView viewListing(
            @PathVariable String uuid,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        session.setAttribute("uuid", uuid);
        Optional<Listing> listing = searchService.searchListingByUuid(uuid);
        if (listing.isPresent()) {
            mav.addObject("listing", listing.get());
            logger.info("Website: " + listing.get().getWebsite());
            mav.addObject("gmapSrc", gmapService.getGmapsUrlString(listing.get()));
            mav.addObject("comments", commentService.getComments(session));
            mav.setViewName("listing");
        } else {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("username",
                    (String) session.getAttribute("username"));
            mav.setViewName("badrequest");
        }
        return mav;
    }

    @PostMapping(path = "/{uuid}")
    public ModelAndView postComment(
            @PathVariable String uuid,
            @RequestBody MultiValueMap<String, String> form,
            HttpSession session) {
        logger.info("calling viewListing");
        ModelAndView mav = viewListing(uuid, session);
        logger.info("called viewListing successfully");
        if (mav.getStatus() == HttpStatus.BAD_REQUEST) {
            logger.info("Failed to get listing from viewListing controller method");
            return mav;
        }
        // view should be listing at this point
        try {
            commentService.postComment(form, session);
            logger.info("Posted comment successfully");
            mav.addObject("comments", commentService.getComments(session));
        } catch (RuntimeException e) {
            logger.warning(e.getMessage());
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("username",
                    (String) session.getAttribute("username"));
            mav.addObject("errormessage", e.getMessage());
            mav.setViewName("badrequest");
        }
        return mav;
    }
}

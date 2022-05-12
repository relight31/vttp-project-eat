package com.vttp.miniproject.Project.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Listing;
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

@Controller
@RequestMapping(path = "/auth/")
public class SearchController {
    Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private TIHSearchService searchService;

    @Autowired
    private GmapService gmapService;

    @GetMapping(path = "/search")
    public ModelAndView searchPage(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", session.getAttribute("username"));
        mav.setViewName("search");
        return mav;
    }

    @GetMapping(path = "/results")
    public ModelAndView chooseForMe() {
        ModelAndView mav = new ModelAndView();
        // search is executing even if not authenticated??
        Optional<List<Listing>> listings = searchService.chooseRandom3();
        if (listings.isPresent()) {
            mav.addObject("listings", listings.get());
        }
        mav.setViewName("results");
        return mav;
    }

    @PostMapping(path = "/results")
    public ModelAndView keywordSearch(@RequestBody MultiValueMap<String, String> form) {
        ModelAndView mav = new ModelAndView();
        String keyword = form.getFirst("keyword");
        Optional<List<Listing>> listings = searchService.keywordChoose3(keyword);
        if (listings.isPresent()) {
            mav.addObject("listings", listings.get());
        } else {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            // TODO bad request
        }
        mav.setViewName("results");
        return mav;
    }

    @GetMapping(path = "/listing/{uuid}")
    public ModelAndView viewListing(@PathVariable String uuid) {
        ModelAndView mav = new ModelAndView();
        Optional<Listing> listing = searchService.searchListingByUuid(uuid);
        if (listing.isPresent()) {
            mav.addObject("listing", listing.get());
            logger.info("Website: "+listing.get().getWebsite());
            mav.addObject("gmapSrc", gmapService.getGmapsUrlString(listing.get()));
        } else {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            // TODO bad request view elements
        }
        mav.setViewName("listing");
        return mav;
    }
}

package com.vttp.miniproject.Project.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Listing;
import com.vttp.miniproject.Project.services.FavouriteService;
import com.vttp.miniproject.Project.services.TIHSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    private FavouriteService favService;

    @GetMapping(path = "/search")
    public ModelAndView searchPage(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", session.getAttribute("username"));
        mav.setViewName("search");
        return mav;
    }

    @GetMapping(path = "/results")
    public ModelAndView chooseForMe(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Optional<List<Listing>> listings = searchService.chooseRandom3();
        if (listings.isPresent()) {
            mav.addObject("listings", listings.get());
            mav.setViewName("results");
        } else {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("username",
                    (String) session.getAttribute("username"));
            mav.setViewName("badrequest");
        }
        return mav;
    }

    @PostMapping(path = "/results")
    public ModelAndView keywordSearch(
            @RequestBody MultiValueMap<String, String> form,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String keyword = form.getFirst("keyword");
        Optional<List<Listing>> listings = searchService.keywordChoose3(keyword);
        if (listings.isPresent()) {
            mav.addObject("listings", listings.get());
            mav.setViewName("results");
        } else {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("username",
                    (String) session.getAttribute("username"));
            mav.setViewName("badrequest");
        }
        return mav;
    }

    @GetMapping(path = "/favourites")
    public ModelAndView getFavourites(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Optional<List<Listing>> opt = favService.getFavourites(session);
        if (opt.isEmpty()) {
            // no favourites yet
        } else {
            List<Listing> favourites = opt.get();
            mav.addObject("favourites", favourites);
        }
        mav.setViewName("favourites");
        return mav;
    }

    @PostMapping(path = "/favourites")
    public ModelAndView addToFavourites(
            HttpSession session,
            @RequestBody MultiValueMap<String, String> form) {
        ModelAndView mav = new ModelAndView();
        try {
            favService.addToFavourites(session, form.getFirst("uuid"));
            Optional<List<Listing>> opt = favService.getFavourites(session);
            if (opt.isEmpty()) {
                // no favourites yet
            } else {
                List<Listing> favourites = opt.get();
                mav.addObject("favourites", favourites);
            }
            mav.setViewName("favourites");
            return mav;
        } catch (Exception e) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("username",
                    (String) session.getAttribute("username"));
            mav.addObject("errormessage", e.getMessage());
            mav.setViewName("badrequest");
        }
        return mav;
    }
}

package com.vttp.miniproject.Project.controllers;

import java.util.List;
import java.util.logging.Logger;

import com.vttp.miniproject.Project.models.Listing;
import com.vttp.miniproject.Project.services.TIHSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
    Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    private TIHSearchService searchService;

    @GetMapping(path = "/results")
    public ModelAndView chooseForMe() {
        ModelAndView mav = new ModelAndView();
        List<Listing> listings = searchService.chooseRandom3();
        mav.addObject("listings", listings);
        mav.setViewName("results");
        return mav;
    }

    @PostMapping(path = "/results")
    public ModelAndView keywordSearch(@RequestBody MultiValueMap<String, String> form) {
        ModelAndView mav = new ModelAndView();
        String keyword = form.getFirst("keyword");
        List<Listing> listings = searchService.keywordChoose3(keyword);
        mav.addObject("listings", listings);
        mav.setViewName("results");
        return mav;
    }
}

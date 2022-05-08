package com.vttp.miniproject.Project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
    @GetMapping(path = "/results")
    public ModelAndView keywordSearch(@RequestParam String keyword){
        return null;
    }

    @PostMapping(path = "/results")
    public ModelAndView chooseForMe(){
        return null;
    }
}

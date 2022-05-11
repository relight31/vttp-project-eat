package com.vttp.miniproject.Project.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.services.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    LoginService loginService;

    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        logger.info("Clearing session");
        session.invalidate();
        logger.info("Session cleared, returning to login page");
        return "index";
    }

    @PostMapping(path = "/authenticate")
    public ModelAndView authenticate(
            @RequestBody MultiValueMap<String, String> form,
            HttpSession session) {
        ModelAndView mav = new ModelAndView();
        if (loginService.authenticate(form.getFirst("username"))) {
            logger.info("Authentication successful");
            session.setAttribute("username", form.getFirst("username"));
            logger.info("Username " + form.getFirst("username") + " added to session");
            mav.setViewName("redirect:/auth/search");
        } else {
            logger.info("Authentication failed");
            mav.setStatus(HttpStatus.UNAUTHORIZED);
            mav.setViewName("index");
        }
        return mav;
    }
}

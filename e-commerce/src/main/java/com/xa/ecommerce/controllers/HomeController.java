package com.xa.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public ModelAndView homeDefault() {
        ModelAndView view = new ModelAndView("home");
        return view;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView view = new ModelAndView("home");
        return view;
    }

}

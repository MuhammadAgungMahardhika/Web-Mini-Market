package com.xa.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {
    
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView view = new ModelAndView("login");
        return view;
    }

}

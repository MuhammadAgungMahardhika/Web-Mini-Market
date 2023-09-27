package com.xa.ecommerce.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/")
public class userController {
    @GetMapping("index")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("/user/index");
        return view;
    }

}

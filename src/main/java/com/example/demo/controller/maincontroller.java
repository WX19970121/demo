package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mainpage")
public class maincontroller {

    @RequestMapping("/openpage")
    public ModelAndView MainPageOpen(ModelAndView modelAndView){
        modelAndView.setViewName("main/main");
        return modelAndView;
    }

    //错误提示页面
    @RequestMapping("/error")
    public ModelAndView errorpage(ModelAndView modelAndView){
        modelAndView.setViewName("main/Error");
        return modelAndView;
    }
    
}

package com.example.stareshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping(value={"/index", "/"})
    private String getHomePage(Model model){
        model.addAttribute("something", "You are on home page!");
        return "index";
    }

    @GetMapping("/login")
    private String getLoginPage(){
        return "login";
    }
}

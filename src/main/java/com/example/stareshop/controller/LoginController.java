package com.example.stareshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    private String getLoginPage(Model model){
        model.addAttribute("title", "Welcome to login, traveller!");
        return "login";
    }
}

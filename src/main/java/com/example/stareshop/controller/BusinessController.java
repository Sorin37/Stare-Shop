package com.example.stareshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    @GetMapping("/register")
    private String getRegisterUserPage(){
        return "registerBusiness";
    }
}

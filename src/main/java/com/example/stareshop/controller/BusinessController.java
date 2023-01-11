package com.example.stareshop.controller;

import com.example.stareshop.model.Business;
import com.example.stareshop.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping("/register")
    private String registerBusinessPage(){
        return "registerBusiness";
    }

    @PostMapping("/register")
    private String registerBusiness(@ModelAttribute Business business){
        businessService.addOrUpdate(business);
        return "redirect:/";
    }
}

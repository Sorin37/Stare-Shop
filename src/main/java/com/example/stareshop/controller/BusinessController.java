package com.example.stareshop.controller;

import com.example.stareshop.model.Business;
import com.example.stareshop.model.User;
import com.example.stareshop.services.BusinessService;
import com.example.stareshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;
    private final UserService userService;

    @GetMapping("/register")
    private String registerBusinessPage(){
        //getting the user's role
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<SimpleGrantedAuthority> authList = new ArrayList<>(authorities);
        List<String> roles = new ArrayList<>();
        for (SimpleGrantedAuthority auth :
                authList) {
            roles.add(auth.toString());
        }

        if(roles.contains("B2CAdmin") || roles.contains("B2BAdmin")){
            return "redirect:/errorAlreadyHasBusiness";
        }

        return "registerBusiness";
    }

    @PostMapping("/register")
    private String registerBusiness(@ModelAttribute Business business){
        businessService.addOrUpdate(business);
        Optional<Business> insertedBusiness = businessService.getByName(business.getName());

        if(insertedBusiness.isPresent()){
            Optional<User> currentUser = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if(currentUser.isPresent()){
                //updateaza si in security context holder
                currentUser.get().setBusinesses(insertedBusiness.get());
                currentUser.get().setRole(insertedBusiness.get().getType() + "Admin");
                userService.addOrUpdateUser(currentUser.get());
            }
        }


        return "redirect:/";
    }

    @GetMapping("/errorAlreadyHasBusiness")
    private String errorAlreadyHasBusiness(){
        return "errorAlreadyHasBusiness";
    }
}

package com.example.stareshop.controller;

import com.example.stareshop.model.User;
import com.example.stareshop.repository.UserRepository;
import com.example.stareshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @GetMapping("xd")
    public ResponseEntity login(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Parola1");
        return ResponseEntity.ok(userService.login("email@email.com", "Parola1"));
    }
}

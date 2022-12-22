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

import java.util.Optional;

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

    @PostMapping("register")
    public void register(@RequestBody User user){
        userService.addUser(user);
    }

    @GetMapping("login")
    public ResponseEntity login(){
        Optional<User> retrievedUser = userService.login("sorin@gmail.com", "xd");
        return ResponseEntity.ok(retrievedUser);
    }
}

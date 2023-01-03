package com.example.stareshop.controller;

import com.example.stareshop.model.User;
import com.example.stareshop.repository.UserRepository;
import com.example.stareshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("register")
    public void register(@RequestBody User user){
        user.setRole("Client");
        userService.addOrUpdateUser(user);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody User user){
        Optional<User> retrievedUser = userService.login(user.getEmail(), user.getPassword());
        if(retrievedUser.isPresent()) {
            return ResponseEntity.ok(retrievedUser);
        }else{
            return ResponseEntity.ok("User not found");
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id){
        if(userService.deleteUser(id)) {
            return ResponseEntity.ok("User got deleted");
        }else{
            return ResponseEntity.ok("User not found");
        }
    }

    @GetMapping("/register")
    private String getRegisterUserPage(){
        return "registerUser";
    }

//    @GetMapping("/xd")
//    private ResponseEntity xd(){
//        User user = new User();
//        user.setEmail("pareri");
//        user.setPassword("pareri");
//        user.setRole("pareri");
//        userService.addOrUpdateUser(user);
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
}

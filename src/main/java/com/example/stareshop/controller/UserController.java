package com.example.stareshop.controller;

import com.example.stareshop.model.User;
import com.example.stareshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
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

    @PostMapping("/register")
    public void register(@RequestBody User user){
        if(!Objects.equals(user.getPassword(), user.getPasswordConfirm())){
//            redirectAttributes.addAttribute("errorMessage", "The passwords don't match!");
//            return "redirect:/user/error";
        }

        user.setRole("Client");
        userService.addOrUpdateUser(user);

//        return "redirect:/login";
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

    @GetMapping("/error")
    private String error(Model model){
        if(model.getAttribute("errorMessage") == null){
            model.addAttribute("errorMessage", "");
        }
        return "errorWithMessage";
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

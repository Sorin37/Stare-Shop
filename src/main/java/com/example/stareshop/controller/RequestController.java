package com.example.stareshop.controller;

import com.example.stareshop.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(requestService.getAll());
    }
}

package com.example.stareshop.controller;

import com.example.stareshop.services.PendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pending")
@RequiredArgsConstructor
public class PendingController {
    private final PendingService pendingService;

    @GetMapping("/raw")
    public ResponseEntity getAllPending(){
        return ResponseEntity.ok(pendingService.getAll());
    }
}

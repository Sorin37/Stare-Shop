package com.example.stareshop.controller;

import com.example.stareshop.model.Request;
import com.example.stareshop.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    public final RequestService requestService;

    @GetMapping
    public ResponseEntity getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    //for now the post only takes as argument the is_active field;
    //can't put b2bid or b2cid in post request
    @PostMapping
    public ResponseEntity addNewRequest(@RequestBody Request request){
        requestService.addRequest(request);
        return ResponseEntity.ok("Request was added successfully");
    }
}

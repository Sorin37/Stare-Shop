package com.example.stareshop.services;

import com.example.stareshop.model.Request;
import com.example.stareshop.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public List<Request> getAllRequests(){
        return new ArrayList<>(requestRepository.findAll());
    }

    public void addRequest(Request request){
        requestRepository.save(request);
    }
}

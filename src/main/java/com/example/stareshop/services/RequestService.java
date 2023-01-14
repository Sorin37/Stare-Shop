package com.example.stareshop.services;

import com.example.stareshop.model.Inventory;
import com.example.stareshop.model.Request;
import com.example.stareshop.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    public List<Request> getAll(){
        return new ArrayList<>(requestRepository.findAll());
    }

    public void add(Request request){
        requestRepository.save(request);
    }

    public List<Request> getAllByB2B(Long b2b_id){
        List<Request> requestList = getAll();
        List<Request> filteredRequests = new ArrayList<>();

        for (Request request :
                requestList) {
            if (Objects.equals(request.getInventory().getBusiness().getId(), b2b_id)) {
                filteredRequests.add(request);
            }
        }
        return filteredRequests;
    }
}

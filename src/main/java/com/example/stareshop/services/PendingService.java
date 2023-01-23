package com.example.stareshop.services;

import com.example.stareshop.model.Pending;
import com.example.stareshop.model.Product;
import com.example.stareshop.repository.PendingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PendingService {
    private final PendingRepository pendingRepository;

    public List<Pending> getAll(){
        return new ArrayList<>(pendingRepository.findAll());
    }

    public void add(Pending pending){
        pendingRepository.save(pending);
    }
}

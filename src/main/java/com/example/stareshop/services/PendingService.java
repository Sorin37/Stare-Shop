package com.example.stareshop.services;

import com.example.stareshop.model.Pending;
import com.example.stareshop.model.Product;
import com.example.stareshop.repository.PendingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean hasPendingAlready(Long userId){
        Optional<Pending> pending = pendingRepository.findByUser_Id(userId);

        if(pending.isPresent()){
            return true;
        }

        return false;
    }
}

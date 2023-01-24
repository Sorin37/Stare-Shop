package com.example.stareshop.services;

import com.example.stareshop.model.Business;
import com.example.stareshop.model.User;
import com.example.stareshop.repository.BusinessRepository;
import com.example.stareshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository businessRepository;

    public List<Business> getAll(){
        return new ArrayList<>(businessRepository.findAll());
    }

    public void addOrUpdate(Business business){
        businessRepository.save(business);
    }

    public Optional<Business> getByName(String name){
        return businessRepository.findByName(name);
    }

    public Optional<Business> getById(Long id){
        return businessRepository.findById(id);
    }

    public void delete(Long id){
        businessRepository.deleteById(id);
    }

    public List<Business> getAllAccepted(){
        var businesses = new ArrayList<>(businessRepository.findAll());
        ArrayList<Business> accepted = new ArrayList<>();
        for (Business b: businesses) {
            if(b.getIsAccepted()){
                accepted.add(b);
            }
        }
        return accepted;
    }
}

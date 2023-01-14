package com.example.stareshop.services;

import com.example.stareshop.model.Business;
import com.example.stareshop.model.Inventory;
import com.example.stareshop.model.Product;
import com.example.stareshop.model.User;
import com.example.stareshop.model.Inventory;
import com.example.stareshop.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<Business> getBusinessesThatHaveProducts(){
        List<Inventory> inventories = getAll();
        List<Business> businesses = new ArrayList<>();

        for (Inventory inventory :
                inventories) {
            if (Objects.equals(inventory.getBusiness().getType(), "B2B")){
                if (!businesses.contains(inventory.getBusiness())) {
                    businesses.add(inventory.getBusiness());
                }
            }
        }

        return businesses;
    }

    public List<Product> getProductsOfABusiness(Long businessId){
        List<Inventory> inventories = inventoryRepository.findAllByBusiness_Id(businessId);
        List<Product> products = new ArrayList<>();

        for (Inventory inventory :
                inventories) {
            products.add(inventory.getProduct());
        }

        return products;
    }

    public List<Inventory> getAll(){
        return new ArrayList<>(inventoryRepository.findAll());
    }

    public Optional<Inventory> getByBusinessAndProduct(Long businessId, Long productId) {
        return inventoryRepository.findByBusiness_IdAndProduct_Id(businessId, productId);
    }

    public void addOrUpdateInventory(Inventory inventory){
        inventoryRepository.save(inventory);
    }
}

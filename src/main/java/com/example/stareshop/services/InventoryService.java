package com.example.stareshop.services;

import com.example.stareshop.model.Inventory;
import com.example.stareshop.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public void addOrUpdateInventory(Inventory inventory){
        inventoryRepository.save(inventory);
    }
}

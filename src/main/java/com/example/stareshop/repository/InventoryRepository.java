package com.example.stareshop.repository;

import com.example.stareshop.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByBusiness_Id(Long business_Id);
    Optional<Inventory> findByBusiness_IdAndProduct_Id(Long business_Id, Long product_Id);
}

package com.example.stareshop.repository;

import com.example.stareshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product p where p.name like %:keyword% or p.producer like %:keyword%", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String Keyword);
}

package com.example.stareshop.services;

import com.example.stareshop.model.Product;
import com.example.stareshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return new ArrayList<>(productRepository.findAll());
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public boolean deleteProduct(Long id){
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> getProductsByKeyword(String keyword){
        return productRepository.findByKeyword(keyword);
    }
}

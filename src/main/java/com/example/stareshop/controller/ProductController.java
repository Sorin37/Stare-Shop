package com.example.stareshop.controller;

import com.example.stareshop.model.Product;
import com.example.stareshop.services.ProductService;
import com.example.stareshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public String getProductsPage(Model model){
        model.addAttribute("allProducts", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return ResponseEntity.ok("Product was added successfully");
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestParam Long id){
        if(productService.deleteProduct(id)) {
            return ResponseEntity.ok("Product got deleted");
        }
        return ResponseEntity.ok("Product could not be found");
    }
}

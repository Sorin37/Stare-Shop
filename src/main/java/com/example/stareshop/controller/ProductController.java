package com.example.stareshop.controller;

import com.example.stareshop.model.Product;
import com.example.stareshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/newProduct")
    public String getNewProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProduct";
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("addNewProduct")
    public ResponseEntity addNewProduct(@ModelAttribute("product") Product product){
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

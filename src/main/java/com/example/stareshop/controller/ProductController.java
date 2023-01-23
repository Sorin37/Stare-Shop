package com.example.stareshop.controller;

import com.example.stareshop.model.Business;
import com.example.stareshop.model.Inventory;
import com.example.stareshop.model.Product;
import com.example.stareshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getProductsPage(Model model){
        model.addAttribute("allProducts", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/raw")
    public ResponseEntity getProds(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/add")
    public String getNewProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProduct";
    }

    @PostMapping("/{id}")
    public ModelAndView getProductDetailPageEmpty(@PathVariable Long id){
        List<Product> products = productService.getAllProducts();
        Product requiredProduct = new Product();

        for(Product product : products){
            if(product.getId().equals(id)){
                requiredProduct = product;
            }
        }

        Set<Inventory> inventory = requiredProduct.getInventory();
        List<Inventory> inventoryList = new ArrayList<>(inventory);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetails");
        modelAndView.addObject(requiredProduct);
        modelAndView.addObject(inventoryList);
        return modelAndView;
    }

    @PostMapping("add")
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

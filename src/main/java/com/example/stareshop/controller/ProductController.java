package com.example.stareshop.controller;

import com.example.stareshop.model.Product;
import com.example.stareshop.services.ProductService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/newProduct")
    public String getNewProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "newProduct";
    }

    //@GetMapping("/productDetails/{productId}/")
    @PostMapping("/productDetails/{id}")
    public ModelAndView getProductDetailPageEmpty(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetails");
        //modelAndView.addObject(product);
        return modelAndView;
    }
    //daca pun @RequestBody imi da 415
    //daca pun @ModelAttribute imi da null in product

    //cand e get, imi da 400  - Bad Request
    //cand e post, imi da 415 - Unsupported Media Type

//    @GetMapping("/productDetails")
//    public String getProductDetailPageEmpty(Model model){
//        return "productDetails";
//    }

//    @PostMapping("/productDetails")
//    public String getProductDetailPageWithData(@RequestBody Product product, Model model){
//        model.addAttribute("product", product);
//        return "productDetails";
//    }

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

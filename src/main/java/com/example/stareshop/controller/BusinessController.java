package com.example.stareshop.controller;

import com.example.stareshop.model.Business;
import com.example.stareshop.model.Inventory;
import com.example.stareshop.model.Product;
import com.example.stareshop.model.User;
import com.example.stareshop.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;
    private final UserService userService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final BusinessValidationService businessValidationService;

    @GetMapping("/register")
    private String registerBusinessPage(Model model){
        //getting the user's role
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<SimpleGrantedAuthority> authList = new ArrayList<>(authorities);
        List<String> roles = new ArrayList<>();
        for (SimpleGrantedAuthority auth :
                authList) {
            roles.add(auth.toString());
        }

        if(roles.contains("B2CAdmin") || roles.contains("B2BAdmin")){
            return "redirect:/errorAlreadyHasBusiness";
        }
        model.addAttribute("business", new Business());
        return "registerBusiness";
    }

    @GetMapping("/{id}")
    public ModelAndView getQuantityProductChangePage(@PathVariable Long id){

        List<Business> businessList = businessService.getAll();
        List<Product> productList = productService.getAllProducts();

        Business requiredBusiness = new Business();

        for (Business business: businessList){
            if(business.getId().equals(id)){
                requiredBusiness = business;
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("quantityChange");
        modelAndView.addObject(requiredBusiness);
        modelAndView.addObject(productList);

        return modelAndView;
    }

    @PostMapping("/register")
    private String registerBusiness(@ModelAttribute Business business, BindingResult bindingResult){
        businessValidationService.validate(business, bindingResult);

        if(bindingResult.hasErrors()){
            return "/registerBusiness";
        }

        businessService.addOrUpdate(business);
        Optional<Business> insertedBusiness = businessService.getByName(business.getName());

        if(insertedBusiness.isPresent()){
            Optional<User> currentUser = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if(currentUser.isPresent()){
                //updateaza si in security context holder
                currentUser.get().setBusinesses(insertedBusiness.get());
                currentUser.get().setRole(insertedBusiness.get().getType() + "Admin");
                userService.addOrUpdateUser(currentUser.get());
            }
        }
        return "redirect:/";
    }

    @GetMapping("/errorAlreadyHasBusiness")
    private String errorAlreadyHasBusiness(){
        return "errorAlreadyHasBusiness";
    }

    @PostMapping("/changeQuantity")
    private ResponseEntity changeQuantity(@RequestParam Long id,
                                          @RequestParam int quantity){

        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            return ResponseEntity.ok("Quantity could not be changed!");
        }
        List<Inventory> inventoryList =  new ArrayList<>(product.get().getInventory());
        inventoryList.get(0).setQuantity(Long.parseLong(String.valueOf(quantity)));

        inventoryService.addOrUpdateInventory(inventoryList.get(0));

        return ResponseEntity.ok("Quantity changed successfully!");
    }

//    @GetMapping("/errorChangingQuantity")
//    private String errorChangingQuantity(Model model) {
//        List<Business> businessList = businessService.getAll();
//        List<Product> productList = productService.getAllProducts();
//
//        Business requiredBusiness = new Business();
//
//        for (Business business: businessList){
//            if(business.getId().equals(Long.parseLong("1"))){
//                requiredBusiness = business;
//            }
//        }
//        model.addAttribute("error", true);
//        model.addAttribute(requiredBusiness);
//        model.addAttribute(productList);
//        return "quantityChange";
//    }

//    @GetMapping("/successChangingQuantity")
//    private String changedSuccessful(Model model) {
//        List<Business> businessList = businessService.getAll();
//        List<Product> productList = productService.getAllProducts();
//
//        Business requiredBusiness = new Business();
//
//        for (Business business: businessList){
//            if(business.getId().equals(Long.parseLong("1"))){
//                requiredBusiness = business;
//            }
//        }
//        model.addAttribute("success", true);
//        model.addAttribute(requiredBusiness);
//        model.addAttribute(productList);
//        return "quantityChange";
//    }
}

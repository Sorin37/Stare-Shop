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

        if(roles.contains("BToCAdmin") || roles.contains("BToBAdmin")){
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

                if(Objects.equals(insertedBusiness.get().getType(), "B2C")){
                    userService.updateRole(currentUser.get().getId(), "BToCAdmin", insertedBusiness.get().getId());
                }else if(Objects.equals(insertedBusiness.get().getType(), "B2B")){
                    userService.updateRole(currentUser.get().getId(), "BToBAdmin", insertedBusiness.get().getId());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/errorAlreadyHasBusiness")
    private String errorAlreadyHasBusiness(){
        return "errorAlreadyHasBusiness";
    }

    @PostMapping("/changeQuantity")
    private ModelAndView changeQuantity(@RequestParam Long id, @RequestParam Long quantity, @RequestParam Long businessId){

        Optional<Product> product = productService.getProductById(id);

        ModelAndView modelAndView = new ModelAndView();

        if(product.isEmpty()){
            modelAndView.setViewName("errorWithMessage");
            modelAndView.addObject("errorMessage", "No product with this id found!");
            return modelAndView;
        }

        Optional<Business> business = businessService.getById(businessId);

        if(business.isEmpty()){
            modelAndView.setViewName("errorWithMessage");
            modelAndView.addObject("errorMessage", "No business with this id found!");
            return modelAndView;
        }

        Optional<Inventory> inventory = inventoryService.getByBusinessAndProduct(businessId, product.get().getId());

        if(inventory.isEmpty()){
            inventory = Optional.of(new Inventory(
                    null,
                    business.get(),
                    product.get(),
                    quantity
                    ));
        }

        inventory.get().setQuantity(quantity);

        inventoryService.addOrUpdateInventory(inventory.get());

        return new ModelAndView("redirect:/product");
    }

    @GetMapping("redirect")
    public ModelAndView redirect(){
        Optional<User> currentUser = userService.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        ModelAndView modelAndView = new ModelAndView();

        //debug this

        if(currentUser.isPresent()){
            if (Objects.equals(currentUser.get().getRole(), "Admin")){
                modelAndView.setViewName("errorWithMessage");
                modelAndView.addObject("errorMessage", "Not implemented for admin yet!");
                return modelAndView;
            }else if (Objects.equals(currentUser.get().getRole(), "BToCAdmin")){
                return new ModelAndView("redirect:/request/" + currentUser.get().getBusinesses().getId());
            }else if(Objects.equals(currentUser.get().getRole(), "BToBAdmin")){
                return new ModelAndView("redirect:/business/" + currentUser.get().getBusinesses().getId());
            }else if(Objects.equals(currentUser.get().getRole(), "Client")){
                return new ModelAndView("redirect:/business/register");
            }
        }else{
            modelAndView.setViewName("errorWithMessage");
            modelAndView.addObject("errorMessage", "The current user was not found!");
            return modelAndView;
        }

        modelAndView.setViewName("errorWithMessage");
        modelAndView.addObject("errorMessage", "Something went wrong...");
        return modelAndView;
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

package com.example.stareshop.controller;

import com.example.stareshop.model.Business;
import com.example.stareshop.model.Inventory;
import com.example.stareshop.model.Product;
import com.example.stareshop.model.Request;
import com.example.stareshop.services.BusinessService;
import com.example.stareshop.services.InventoryService;
import com.example.stareshop.services.ProductService;
import com.example.stareshop.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final BusinessService businessService;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(requestService.getAll());
    }

    @GetMapping("/{businessId}")
    public ModelAndView getQuantityProductChangePage(@PathVariable Long businessId) {

        Optional<Business> business = businessService.getById(businessId);
        List<Business> businessesList = inventoryService.getBusinessesThatHaveProducts();

        Business firstBusiness = new Business();

        if (businessesList.size() == 0) {
            firstBusiness.setName("N-avem");
        } else {
            firstBusiness = businessesList.get(0);
        }

        List<Product> productsList = new ArrayList<>();

        if (firstBusiness.getId() != null) {
            productsList = inventoryService.getProductsOfABusiness(firstBusiness.getId());
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("request");
        modelAndView.addObject(business.get());
        modelAndView.addObject(businessesList);
        modelAndView.addObject(productsList);

        return modelAndView;
    }

    @PostMapping("/dropdown")
    public ModelAndView changeProducts(@RequestParam Long businessId) {
        List<Product> productsList = new ArrayList<>();
        Long selectedBusinessId = businessId;
        Optional<Business> business = businessService.getById(businessId);
        List<Business> businessesList = inventoryService.getBusinessesThatHaveProducts();

        Business selectedBusiness = new Business();

        for (Business businessObject :
                businessesList) {
            if (Objects.equals(businessObject.getId(), selectedBusinessId)) {
                selectedBusiness = businessObject;
            }
        }

        businessesList.remove(selectedBusiness);

        List<Business> sortedBusinesses = new ArrayList<>();
        sortedBusinesses.add(selectedBusiness);

        for (Business businessObject :
                businessesList) {
            sortedBusinesses.add(businessObject);
        }
        ModelAndView modelAndView = new ModelAndView();

        if (businessId != null) {
            productsList = inventoryService.getProductsOfABusiness(businessId);
            modelAndView.setViewName("request");
            modelAndView.addObject(business.get());
            modelAndView.addObject(sortedBusinesses);
            modelAndView.addObject(productsList);
            modelAndView.addObject(selectedBusinessId);
        }

        return modelAndView;
    }

    @PostMapping("/submit")
    public String submit(@RequestParam Long businessId, @RequestParam Long productId, @RequestParam Long quantity) {
        Request request = new Request();
        Optional<Inventory> inventory = inventoryService.getByBusinessAndProduct(businessId, productId);

        if(inventory.isEmpty()){
            System.out.println("Nu s-a gasit inventarul");
        }

        Optional<Business> b2c = businessService.getById(businessId);

        if(b2c.isEmpty()){
            System.out.println("Nu s-a gasit b2c-ul!");
        }

        request.setB2c(b2c.get());
        request.setInventory(inventory.get());
        request.setQuantity(quantity);
        request.setAccepted(false);

        requestService.add(request);

        return "redirect:/request";
    }


}

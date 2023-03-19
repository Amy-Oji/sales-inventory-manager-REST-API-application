package com.amyojiakor.sales.inventory.manager.app.controllers;

import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderDTO;
import com.amyojiakor.sales.inventory.manager.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer/")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("order-products")
    public ResponseEntity<?> placeOrder(@RequestBody CustomerOrderDTO customerOrderDTO) throws Exception {

        return ResponseEntity.ok(customerService.placeOrder(customerOrderDTO));
    }
}

package com.amyojiakor.sales.inventory.manager.app.controllers;

import com.amyojiakor.sales.inventory.manager.app.models.dto.ProductDTO;
import com.amyojiakor.sales.inventory.manager.app.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/")
public class AdminController {
    private final AdminServices adminServices;
    @Autowired
    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }

    @PostMapping("create-product")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) throws Exception {

        return ResponseEntity.ok(adminServices.createNewProduct(productDTO));
    }

    @PostMapping("update-product/{product-id}")
    public ResponseEntity<?> updateProduct(@PathVariable("product-id") long productId, @RequestBody ProductDTO productDTO) throws Exception {

        return ResponseEntity.ok(adminServices.updateProduct(productId, productDTO));
    }

    @GetMapping("get-all-available-products")
    public ResponseEntity<?> getAvailableProducts(){

        return ResponseEntity.ok(adminServices.getAvailableAllProducts());
    }
}

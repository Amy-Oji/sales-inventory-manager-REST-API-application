package com.amyojiakor.sales.inventory.manager.app.services;


import com.amyojiakor.sales.inventory.manager.app.models.dto.ProductDTO;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Product;

import java.util.List;

public interface AdminServices {


    Product createNewProduct(ProductDTO productDTO) throws Exception;
    Product updateProduct(Long productID, ProductDTO productDTO) throws Exception;
    List<Product> getAvailableAllProducts();


}

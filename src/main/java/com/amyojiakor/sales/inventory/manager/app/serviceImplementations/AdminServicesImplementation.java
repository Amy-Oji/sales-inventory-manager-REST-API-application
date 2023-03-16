package com.amyojiakor.sales.inventory.manager.app.serviceImplementations;

import com.amyojiakor.sales.inventory.manager.app.models.dto.ProductDTO;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Product;
import com.amyojiakor.sales.inventory.manager.app.repositories.CustomerRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.OrderRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.ProductRepository;
import com.amyojiakor.sales.inventory.manager.app.services.AdminServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServicesImplementation implements AdminServices {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public AdminServicesImplementation(CustomerRepository customerRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product createNewProduct(ProductDTO productDTO) throws Exception {

        int exists  = productRepository.checkIfProductAlreadyExists(productDTO.getName());
        if(exists != 0){
            throw new Exception("Product with name '" + productDTO.getName() + "' already exists. " +
                    " Update the existing product instead or create another one with a unique name");
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        product.setAvailable();
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Long productID, ProductDTO productDTO) throws Exception {
       Product product = productRepository.findById(productID)
                .orElseThrow(()-> new Exception("Product with id number "+ productID+ " not found"));
       BeanUtils.copyProperties(productDTO, product);
       product.setAvailable();
       productRepository.save(product);
       return product;
    }

    @Override
    public List<Product> getAvailableAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .filter(Product::isAvailable)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

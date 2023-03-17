package com.amyojiakor.sales.inventory.manager.app;

import com.amyojiakor.sales.inventory.manager.app.models.dto.ProductDTO;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Order;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Product;
import com.amyojiakor.sales.inventory.manager.app.repositories.ProductRepository;
import com.amyojiakor.sales.inventory.manager.app.serviceImplementations.AdminServicesImplementation;
import com.amyojiakor.sales.inventory.manager.app.services.AdminServices;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AdminServicesImplementation adminServicesImplementation;

    @Test
    public void testCreateNewProduct() throws Exception {
        // Create a productDTO to pass to the method
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("A test product");
        productDTO.setPrice(9.99);

        // Mock the productRepository and set up its behavior
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        when(productRepository.checkIfProductAlreadyExists(anyString())).thenReturn(0);

        // Create the instance of the class that contains the method being tested
        AdminServicesImplementation adminServicesImplementation = new AdminServicesImplementation(productRepository);

        // Call the method being tested
        Product product = adminServicesImplementation.createNewProduct(productDTO);

        // Verify that the save method of the productRepository was called with the correct product object

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Verify that the created product object has the correct properties
        assertEquals("Test Product", product.getName());
        assertEquals("A test product", product.getDescription());
        assertEquals(9.99, product.getPrice(), 0.001);
    }

}





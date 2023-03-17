package com.amyojiakor.sales.inventory.manager.app;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amyojiakor.sales.inventory.manager.app.models.dto.ProductDTO;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Product;
import com.amyojiakor.sales.inventory.manager.app.repositories.CustomerRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.OrderRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.ProductRepository;
import com.amyojiakor.sales.inventory.manager.app.serviceImplementations.AdminServicesImplementation;
import com.amyojiakor.sales.inventory.manager.app.services.AdminServices;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AdminServicesImplementation.class})
class AdminServiceTest {

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private AdminServices serviceUnderTest;

    @Test
    void createNewProduct_() {
        final ProductDTO productDTO = getProductDTO();
        when(productRepository.checkIfProductAlreadyExists(anyString())).thenReturn(1);

        final ThrowableAssert.ThrowingCallable throwingCallable = () -> serviceUnderTest.createNewProduct(productDTO);
        assertThatThrownBy(throwingCallable).isInstanceOf(Exception.class);
    }

    @Test
    void createNewProduct() throws Exception {
        final ProductDTO productDTO = getProductDTO();
        when(productRepository.checkIfProductAlreadyExists(anyString())).thenReturn(0);
        final Product response = serviceUnderTest.createNewProduct(productDTO);

        assertAll(
                () -> assertThat(response.isAvailable()).isTrue(),
                () -> assertThat(response).isEqualTo(new Product(null, "test", 1.19, "test description", 2, true))
        );
    }

    private static ProductDTO getProductDTO() {
        return new ProductDTO("test", 1.19, "test description", 2);
    }

    private static Product getProduct(final Long id) {
        return new Product(id, "test", 1.19, "test description", 2, true);
    }

    private static Product getProduct() {
        return getProduct(null);
    }

    @Test
    void updateProduct_() throws Exception {
        final ProductDTO productDTO = getProductDTO();
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        ThrowableAssert.ThrowingCallable throwingCallable = () -> serviceUnderTest.updateProduct(9L, productDTO);
        assertThatThrownBy(throwingCallable).isInstanceOf(Exception.class);
    }


    @Test
    void updateProduct() throws Exception {
        final ProductDTO productDTO = new ProductDTO("test", 100, "test description", 2);
        final Product product = getProduct(9L);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        final Product response = serviceUnderTest.updateProduct(9L, productDTO);
        assertAll(
                () -> assertThat(response.getAmountInStock()).isEqualTo(2),
                () -> assertThat(response.getPrice()).isEqualTo(100)
        );
    }

    @Test
    void getAllAvailableProducts_() {
        when(productRepository.findAll()).thenReturn(List.of());
        final List<Product> response = serviceUnderTest.getAvailableAllProducts();
        assertThat(response).isEmpty();
    }

    @Test
    void getAllAvailableProducts() {
        final Product product1 = getProduct(1L);
        final Product product2 = getProduct(2L);
        final Product product3 = getProduct(3L);
        when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3));
        final List<Product> response = serviceUnderTest.getAvailableAllProducts();
        assertThat(response).containsExactly(product1, product2, product3);
    }
}
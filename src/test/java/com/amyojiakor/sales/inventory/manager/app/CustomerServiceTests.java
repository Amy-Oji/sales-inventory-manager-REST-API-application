package com.amyojiakor.sales.inventory.manager.app;

import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderDTO;
import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderResponse;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Customer;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Order;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Product;
import com.amyojiakor.sales.inventory.manager.app.repositories.CustomerRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.OrderRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.ProductRepository;
import com.amyojiakor.sales.inventory.manager.app.serviceImplementations.CustomerServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTests {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private KafkaTemplate<String, CustomerOrderResponse> kafkaTemplate;

	@InjectMocks
	private CustomerServiceImplementation customerServiceImplementation;

	@Test
	public void testPlaceOrder() throws Exception {
		// Mock data
		CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO();
		customerOrderDTO.setCustomerPhoneNum("1234567890");
		customerOrderDTO.setProducts(Map.of(1L, 2, 2L, 3));

		Customer customer = new Customer();
		customer.setId(1L);
		customer.setPhoneNum(customerOrderDTO.getCustomerPhoneNum());
		when(customerRepository.findCustomerByPhoneNum(customerOrderDTO.getCustomerPhoneNum())).thenReturn(customer);

		Product product1 = new Product();
		product1.setId(1L);
		product1.setName("Product 1");
		product1.setPrice(10.0);
		product1.setAmountInStock(5);
		when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Product 2");
		product2.setPrice(15.0);
		product2.setAmountInStock(3);
		when(productRepository.findById(2L)).thenReturn(Optional.of(product2));

		Order order = new Order();
		when(orderRepository.save(any(Order.class))).thenReturn(order);

		// Call the method
		CustomerOrderResponse customerOrderResponse = customerServiceImplementation.placeOrder(customerOrderDTO);

		// Assertions
		Assertions.assertNotNull(customerOrderResponse);
		Assertions.assertEquals(customerOrderDTO, customerOrderResponse.getCustomerOrderDTO());
		Assertions.assertEquals(65.0, customerOrderResponse.getSum(), 0.0);
		Assertions.assertNotNull(customerOrderResponse.getOrder_date());
		Assertions.assertEquals(2, customerOrderResponse.getProductDetails().size());
		Assertions.assertTrue(customerOrderResponse.getProductDetails().containsKey(1L));
		Assertions.assertTrue(customerOrderResponse.getProductDetails().containsKey(2L));
		Assertions.assertEquals(10, customerOrderResponse.getProductDetails().get(1L), 0);
		Assertions.assertEquals(15, customerOrderResponse.getProductDetails().get(2L), 0);
		verify(orderRepository, times(1)).save(any(Order.class));
		verify(productRepository, times(1)).save(product1);
		verify(productRepository, times(1)).save(product2);
		verify(kafkaTemplate, times(1)).send(any(), eq(customerOrderResponse));
	}
}







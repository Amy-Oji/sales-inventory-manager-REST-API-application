package com.amyojiakor.sales.inventory.manager.app.serviceImplementations;

import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderDTO;
import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderResponse;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Customer;
import com.amyojiakor.sales.inventory.manager.app.models.entities.Order;
import com.amyojiakor.sales.inventory.manager.app.models.entities.OrderDetails;
import com.amyojiakor.sales.inventory.manager.app.repositories.CustomerRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.OrderDetailsRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.OrderRepository;
import com.amyojiakor.sales.inventory.manager.app.repositories.ProductRepository;
import com.amyojiakor.sales.inventory.manager.app.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
public class CustomerServiceImplementation implements CustomerService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final KafkaTemplate<String, CustomerOrderResponse> kafkaTemplate;
    private final String orderTopic;

    @Autowired
    public CustomerServiceImplementation(OrderRepository orderRepository, ProductRepository productRepository,
                                         CustomerRepository customerRepository, KafkaTemplate<String,
            CustomerOrderResponse> kafkaTemplate, @Value("${kafka.topic.order}") String orderTopic) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.orderTopic = orderTopic;
    }


    @Transactional
    @Override
    public CustomerOrderResponse placeOrder(CustomerOrderDTO customerOrderDTO) throws Exception {
        Order order = new Order();
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        Map<Long, Double> productDetails = new LinkedHashMap<>();
        // If customer phone number is provided, check if customer exists, else create a new customer
        if (customerOrderDTO.getCustomerPhoneNum() != null) {
            Customer customer = getOrCreateCustomer(customerOrderDTO);
            order.setCustomer(customer);
        }

        Map<Long, Integer> orderProducts = customerOrderDTO.getProducts();
        double sum = 0;

        // Loop through ordered products, check availability, and create order details
        for (Map.Entry<Long, Integer> entry : orderProducts.entrySet()) {
            OrderDetails orderDetails = createOrderDetails(entry);
            sum += orderDetails.getPriceXquantity();
            orderDetailsList.add(orderDetails);
            productDetails.put(orderDetails.getProduct().getId(), orderDetails.getProduct().getPrice());
        }

        LocalDateTime orderTime = LocalDateTime.now(ZoneId.of("Africa/Lagos"));

        // Set up order and customerOrderResponse
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
        customerOrderResponse.setCustomerOrderDTO(customerOrderDTO);
        customerOrderResponse.setSum(sum);
        customerOrderResponse.setOrder_date(orderTime);
        customerOrderResponse.setProductDetails(productDetails);

        order.setOrder_date(orderTime);
        order.setSum(sum);
        order.setOrderDetails(orderDetailsList);
        orderRepository.save(order);

        // Save changes to product repository and send Kafka message
        saveOrderDetailsAndSendKafkaMessage(orderDetailsList, customerOrderResponse);

        return customerOrderResponse;
    }

    private Customer getOrCreateCustomer(CustomerOrderDTO customerOrderDTO) {
        Customer customer = customerRepository.findCustomerByPhoneNum(customerOrderDTO.getCustomerPhoneNum());
        if (customer == null) {
            customer = new Customer();
            customer.setName(customerOrderDTO.getCustomerName());
            customer.setPhoneNum(customerOrderDTO.getCustomerPhoneNum());
            customerRepository.save(customer);
        }
        return customer;
    }

    private OrderDetails createOrderDetails(Map.Entry<Long, Integer> entry) throws Exception {
        long productId = entry.getKey();
        int quantity = entry.getValue();

        var product = productRepository.findById(productId).orElseThrow(() -> new Exception("Product with ID '" + productId + " does not exists." +
                " Please make your selection from our existing products"));
        if (quantity > product.getAmountInStock()) {
            throw new Exception("Product ID:" + productId + " with name: '" + product.getName() + "' is currently not available" +
                    " Please make your selection from our available products");
        }

        product.setAmountInStock(product.getAmountInStock() - quantity);
        var price = product.getPrice() * quantity;

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProduct(product);
        orderDetails.setQuantityOrdered(quantity);
        orderDetails.setPricePerUnit(product.getPrice());
        orderDetails.setPriceXquantity(price);

        return orderDetails;
    }

    private void saveOrderDetailsAndSendKafkaMessage(List<OrderDetails> orderDetailsList, CustomerOrderResponse customerOrderResponse) {
        for (OrderDetails orderDetail : orderDetailsList) {
            productRepository.save(orderDetail.getProduct());
        }
        kafkaTemplate.send(orderTopic, customerOrderResponse);
    }


}
package com.amyojiakor.sales.inventory.manager.app.models.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CustomerOrderResponse {
    private CustomerOrderDTO customerOrderDTO;
    private Map<Long, Double> productDetails;
    private double sum;
    private LocalDateTime order_date;
}

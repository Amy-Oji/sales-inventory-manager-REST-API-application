package com.amyojiakor.sales.inventory.manager.app.models.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CustomerOrderDTO {
    private String customerName;
    private String customerPhoneNum;
    private Map<Long, Integer> products;

}

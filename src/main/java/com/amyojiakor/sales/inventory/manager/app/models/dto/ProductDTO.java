package com.amyojiakor.sales.inventory.manager.app.models.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;

    private double price;

    private String description;

    private int amountInStock;
}

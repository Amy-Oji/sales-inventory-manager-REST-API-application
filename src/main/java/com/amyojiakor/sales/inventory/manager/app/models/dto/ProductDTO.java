package com.amyojiakor.sales.inventory.manager.app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;

    private double price;

    private String description;

    private int amountInStock;
}

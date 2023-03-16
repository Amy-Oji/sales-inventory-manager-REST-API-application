package com.amyojiakor.sales.inventory.manager.app.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private String description;
    private int amountInStock;

    private boolean isAvailable;

    public void setAvailable() {
        if(amountInStock > 0){
            isAvailable = true;
        }
    }
}

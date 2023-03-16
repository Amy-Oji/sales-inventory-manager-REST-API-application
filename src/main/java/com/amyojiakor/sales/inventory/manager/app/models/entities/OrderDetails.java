package com.amyojiakor.sales.inventory.manager.app.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantityOrdered;

    private double pricePerUnit;

    private double priceXquantity;


    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("orderDetails")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("orderDetails")
    private Product product;



}

package com.amyojiakor.sales.inventory.manager.app.repositories;

import com.amyojiakor.sales.inventory.manager.app.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

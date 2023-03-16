package com.amyojiakor.sales.inventory.manager.app.repositories;

import com.amyojiakor.sales.inventory.manager.app.models.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}

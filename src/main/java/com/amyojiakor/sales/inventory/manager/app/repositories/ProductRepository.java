package com.amyojiakor.sales.inventory.manager.app.repositories;

import com.amyojiakor.sales.inventory.manager.app.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

 @Query(value="SELECT COUNT(1) FROM products p WHERE p.name = :name",nativeQuery = true)
 int checkIfProductAlreadyExists(@Param("name") String name);

}

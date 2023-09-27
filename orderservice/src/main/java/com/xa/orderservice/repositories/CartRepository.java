package com.xa.orderservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xa.orderservice.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM cart WHERE costumer_id = ?1 ORDER BY id DESC", nativeQuery = true)
    List<Cart> getCartFromCostumerId(Long CostumerId);
}

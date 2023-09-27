package com.xa.orderservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xa.orderservice.models.CartProduct;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query(value = "SELECT * FROM cart_product WHERE cart_id = ?1", nativeQuery = true)
    List<CartProduct> getProductFromCart(Long cart);
}

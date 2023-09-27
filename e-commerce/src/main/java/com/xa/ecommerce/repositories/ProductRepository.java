package com.xa.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.xa.ecommerce.models.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

}

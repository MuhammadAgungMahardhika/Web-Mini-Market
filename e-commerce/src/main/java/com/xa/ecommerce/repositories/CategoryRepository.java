package com.xa.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xa.ecommerce.models.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Long> {

}

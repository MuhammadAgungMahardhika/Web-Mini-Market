package com.xa.storeservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xa.storeservice.models.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    

}

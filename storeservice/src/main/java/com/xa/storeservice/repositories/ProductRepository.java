package com.xa.storeservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.xa.storeservice.models.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query(value = "SELECT * FROM products WHERE name ILIKE %:searchText%", nativeQuery = true)
    List<Products> productByName(@Param("searchText") String searchText);

    @Query(value = "SELECT count(*) as countData FROM Products WHERE sku=?1 AND name=?2", nativeQuery = true)
    public Long getValidate(String sku, String name);

}

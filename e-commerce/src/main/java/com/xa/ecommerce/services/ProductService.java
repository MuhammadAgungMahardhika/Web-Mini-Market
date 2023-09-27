package com.xa.ecommerce.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xa.ecommerce.models.Products;
import com.xa.ecommerce.repositories.ProductRepository;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Products> allProduct() {
        return this.productRepository.findAll();
    }
}

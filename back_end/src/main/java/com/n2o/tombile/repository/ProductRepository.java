package com.n2o.tombile.repository;

import com.n2o.tombile.enums.ProductType;
import com.n2o.tombile.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductType(ProductType productType);
}

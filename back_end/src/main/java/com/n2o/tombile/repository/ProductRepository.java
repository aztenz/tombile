package com.n2o.tombile.repository;

import com.n2o.tombile.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

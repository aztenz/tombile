package com.n2o.tombile.repository.product;

import com.n2o.tombile.model.ProductType;
import com.n2o.tombile.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository<P extends Product>
        extends JpaRepository<P, Integer> {
    List<P> findByProductType(ProductType productType);
    @Query("SELECT p from Product p WHERE p.id = :id " +
            "AND p.supplier.id = :spId")
    Optional<P> findUserProductById(int id, int spId);

    @Query("SELECT p from Product p WHERE p.supplier.id = :spId")
    List<P> findAllUserProducts(int spId);
}

package com.n2o.tombile.product.product.repository;

import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository<P extends Product>
        extends JpaRepository<P, Integer> {
    List<P> findByProductType(ProductType productType);
    @Query("SELECT p from Product p WHERE p.id = :id " +
            "AND p.supplier.id = :spId " +
            "AND p.productType = :pt")
    Optional<P> findUserProductById(int id, int spId, ProductType pt);

    @Query("SELECT p from Product p " +
            "WHERE p.supplier.id = :spId " +
            "AND p.productType = :pt")
    List<P> findAllUserProducts(int spId ,ProductType pt);

    default ProductType getProductType() {
        return ProductType.CAR;
    };
}

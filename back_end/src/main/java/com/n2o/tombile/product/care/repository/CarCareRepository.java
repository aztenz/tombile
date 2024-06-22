package com.n2o.tombile.product.care.repository;

import com.n2o.tombile.product.care.model.CarCare;
import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.repository.ProductRepository;

public interface CarCareRepository extends ProductRepository<CarCare> {
    @Override
    default ProductType getProductType() {
        return ProductType.CAR_SERVICE;
    }
}

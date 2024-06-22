package com.n2o.tombile.product.car.repository;

import com.n2o.tombile.product.car.model.Car;
import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.repository.ProductRepository;

public interface CarRepository extends ProductRepository<Car> {
    @Override
    default ProductType getProductType() {
        return ProductType.CAR;
    }
}

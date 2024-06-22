package com.n2o.tombile.product.part.repository;

import com.n2o.tombile.product.part.model.CarPart;
import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.repository.ProductRepository;

public interface CarPartRepository extends ProductRepository<CarPart> {
    @Override
    default ProductType getProductType() {
        return ProductType.CAR_PART;
    }
}

package com.n2o.tombile.product.care.service;

import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.product.care.dto.RSPPersistCarCare;
import com.n2o.tombile.product.care.model.CarCare;
import com.n2o.tombile.product.care.repository.CarCareRepository;
import com.n2o.tombile.product.product.dto.RSPProductDetails;
import com.n2o.tombile.product.product.dto.RSPProductListItem;
import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CarCareService extends ProductService<CarCare, CarCareRepository> {

    public CarCareService(CarCareRepository productRepository, RoleStrategyFactory roleStrategyFactory) {
        super(productRepository, roleStrategyFactory);
    }

    @Override
    public Class<CarCare> getProductClass() {
        return null;
    }

    @Override
    public Class<RSPPersistCarCare> getPersistProductRSPClass() {
        return RSPPersistCarCare.class;
    }

    @Override
    public Class<? extends RSPProductListItem> getProductListItemClass() {
        return null;
    }

    @Override
    public Class<? extends RSPProductDetails> getProductDetailsClass() {
        return null;
    }


    @Override
    public void setProductSpecificDetails(CarCare product) {
        product.setName("");
        product.setProductType(ProductType.CAR_SERVICE);
    }
}

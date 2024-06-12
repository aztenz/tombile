package com.n2o.tombile.product.care.service;

import com.n2o.tombile.product.care.dto.PersistCarCareRSP;
import com.n2o.tombile.product.product.dto.ProductDetails;
import com.n2o.tombile.product.product.dto.ProductListItem;
import com.n2o.tombile.product.care.model.CarCare;
import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.service.ProductService;
import com.n2o.tombile.product.care.repository.CarCareRepository;
import org.springframework.stereotype.Service;

@Service
public class CarCareService extends ProductService<CarCare, CarCareRepository> {

    public CarCareService(CarCareRepository carCareRepository) {
        super(carCareRepository);
    }

    @Override
    public Class<CarCare> getProductClass() {
        return null;
    }

    @Override
    public Class<PersistCarCareRSP> getPersistProductRSPClass() {
        return PersistCarCareRSP.class;
    }

    @Override
    public Class<? extends ProductListItem> getProductListItemClass() {
        return null;
    }

    @Override
    public Class<? extends ProductDetails> getProductDetailsClass() {
        return null;
    }


    @Override
    public void setProductSpecificDetails(CarCare product) {
        product.setName("");
        product.setProductType(ProductType.CAR_SERVICE);
    }
}

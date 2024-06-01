package com.n2o.tombile.service.product;

import com.n2o.tombile.dto.response.product.abs_product.ProductDetails;
import com.n2o.tombile.dto.response.product.abs_product.ProductListItem;
import com.n2o.tombile.dto.response.product.car_care.*;
import com.n2o.tombile.model.CarCare;
import com.n2o.tombile.repository.product.CarCareRepository;
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

    }
}

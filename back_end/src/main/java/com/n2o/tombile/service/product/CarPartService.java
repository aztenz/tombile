package com.n2o.tombile.service.product;

import com.n2o.tombile.dto.response.product.abs_product.PersistProductRSP;
import com.n2o.tombile.dto.response.product.abs_product.ProductDetails;
import com.n2o.tombile.dto.response.product.abs_product.ProductListItem;
import com.n2o.tombile.dto.response.product.part.PartDetails;
import com.n2o.tombile.dto.response.product.part.PartListItem;
import com.n2o.tombile.dto.response.product.part.PersistPartRSP;
import com.n2o.tombile.model.CarPart;
import com.n2o.tombile.repository.product.CarPartRepository;
import org.springframework.stereotype.Service;

@Service
public class CarPartService extends ProductService<CarPart, CarPartRepository> {

    public CarPartService(CarPartRepository productRepository) {
        super(productRepository);
    }

    @Override
    public Class<CarPart> getProductClass() {
        return CarPart.class;
    }

    @Override
    public Class<? extends PersistProductRSP> getPersistProductRSPClass() {
        return PersistPartRSP.class;
    }

    @Override
    public Class<? extends ProductListItem> getProductListItemClass() {
        return PartListItem.class;
    }

    @Override
    public Class<? extends ProductDetails> getProductDetailsClass() {
        return PartDetails.class;
    }

    @Override
    public void setProductSpecificDetails(CarPart product) {

    }
}

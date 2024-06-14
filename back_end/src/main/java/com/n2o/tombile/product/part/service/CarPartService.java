package com.n2o.tombile.product.part.service;

import com.n2o.tombile.product.part.dto.PartDetails;
import com.n2o.tombile.product.part.dto.PartListItem;
import com.n2o.tombile.product.part.dto.PersistPartRSP;
import com.n2o.tombile.product.part.model.CarPart;
import com.n2o.tombile.product.part.repository.CarPartRepository;
import com.n2o.tombile.product.product.dto.PersistProductRSP;
import com.n2o.tombile.product.product.dto.ProductDetails;
import com.n2o.tombile.product.product.dto.ProductListItem;
import com.n2o.tombile.product.product.model.ProductType;
import com.n2o.tombile.product.product.service.ProductService;
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
        product.setName("");
        product.setProductType(ProductType.CAR_PART);
    }
}

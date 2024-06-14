package com.n2o.tombile.product.part.service;

import com.n2o.tombile.product.part.dto.RSPPartDetails;
import com.n2o.tombile.product.part.dto.RSPPartListItem;
import com.n2o.tombile.product.part.dto.RSPPersistPart;
import com.n2o.tombile.product.part.model.CarPart;
import com.n2o.tombile.product.part.repository.CarPartRepository;
import com.n2o.tombile.product.product.dto.RSPPersistProduct;
import com.n2o.tombile.product.product.dto.RSPProductDetails;
import com.n2o.tombile.product.product.dto.RSPProductListItem;
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
    public Class<? extends RSPPersistProduct> getPersistProductRSPClass() {
        return RSPPersistPart.class;
    }

    @Override
    public Class<? extends RSPProductListItem> getProductListItemClass() {
        return RSPPartListItem.class;
    }

    @Override
    public Class<? extends RSPProductDetails> getProductDetailsClass() {
        return RSPPartDetails.class;
    }

    @Override
    public void setProductSpecificDetails(CarPart product) {
        product.setName("");
        product.setProductType(ProductType.CAR_PART);
    }
}

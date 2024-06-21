package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.RSPPersistProduct;
import com.n2o.tombile.product.care.model.CarCareType;
import lombok.Getter;

@Getter
public class RSPPersistPart extends RSPPersistProduct {
    private String contactInfo;
    private String location;
    private CarCareType carCareType;
}

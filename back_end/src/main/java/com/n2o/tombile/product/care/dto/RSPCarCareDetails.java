package com.n2o.tombile.product.care.dto;

import com.n2o.tombile.product.care.model.CarCareType;
import com.n2o.tombile.product.product.dto.RSPProductDetails;
import lombok.Getter;

@Getter
public class RSPCarCareDetails extends RSPProductDetails {
    private String contactInfo;
    private String location;
    private CarCareType serviceType;
}

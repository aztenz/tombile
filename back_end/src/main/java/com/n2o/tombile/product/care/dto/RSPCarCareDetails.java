package com.n2o.tombile.product.care.dto;

import com.n2o.tombile.product.care.model.CarCareType;
import lombok.Getter;

@Getter
public class RSPCarCareDetails {
    private String contactInfo;
    private String location;
    private CarCareType carCareType;
}

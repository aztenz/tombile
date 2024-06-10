package com.n2o.tombile.dto.response.product.car_care;

import com.n2o.tombile.model.CarCareType;
import lombok.Getter;

@Getter
public class CarCareDetails {
    private String contactInfo;
    private String location;
    private CarCareType carCareType;
}

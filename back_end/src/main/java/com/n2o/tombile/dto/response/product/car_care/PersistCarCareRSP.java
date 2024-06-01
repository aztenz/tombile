package com.n2o.tombile.dto.response.product.car_care;

import com.n2o.tombile.dto.response.product.abs_product.PersistProductRSP;
import com.n2o.tombile.enums.CarCareType;
import lombok.Getter;

@Getter
public class PersistCarCareRSP extends PersistProductRSP {
    private String contactInfo;
    private String location;
    private CarCareType carCareType;
}

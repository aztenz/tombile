package com.n2o.tombile.dto.response.product.part;

import com.n2o.tombile.dto.response.product.abs_product.PersistProductRSP;
import com.n2o.tombile.model.CarCareType;
import lombok.Getter;

@Getter
public class PersistPartRSP extends PersistProductRSP {
    private String contactInfo;
    private String location;
    private CarCareType carCareType;
}

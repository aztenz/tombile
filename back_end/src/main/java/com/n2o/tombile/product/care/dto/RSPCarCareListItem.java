package com.n2o.tombile.product.care.dto;

import com.n2o.tombile.product.care.model.CarCareType;
import com.n2o.tombile.product.product.dto.RSPProductListItem;
import lombok.Getter;

@Getter
public class RSPCarCareListItem extends RSPProductListItem {
    private CarCareType serviceType;
}

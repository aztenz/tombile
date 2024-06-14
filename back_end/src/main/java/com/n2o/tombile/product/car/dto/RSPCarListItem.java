package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.car.model.CarState;
import com.n2o.tombile.product.product.dto.RSPProductListItem;
import lombok.Getter;

@Getter
public class RSPCarListItem extends RSPProductListItem {
    private CarState carState;
}

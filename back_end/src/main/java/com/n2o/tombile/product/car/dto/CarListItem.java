package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.car.model.CarState;
import com.n2o.tombile.product.product.dto.ProductListItem;
import lombok.Getter;

@Getter
public class CarListItem extends ProductListItem {
    private CarState carState;
}

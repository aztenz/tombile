package com.n2o.tombile.dto.response.product.car;

import com.n2o.tombile.dto.response.product.abs_product.ProductListItem;
import com.n2o.tombile.model.CarState;
import lombok.Getter;

@Getter
public class CarListItem extends ProductListItem {
    private CarState carState;
}

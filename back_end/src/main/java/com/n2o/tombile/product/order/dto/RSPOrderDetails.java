package com.n2o.tombile.product.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RSPOrderDetails extends RSPOrderListItem {
    private List<RSPOrderItem> orderItems;
}

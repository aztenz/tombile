package com.n2o.tombile.product.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class RSPOrderListItem {
    private int id;
    private String buyerName;
    private String buyerEmail;
    private double totalPrice;
    private String orderStatus;
    private Instant orderDate;
    private RSPShippingAddress shippingAddress;
}

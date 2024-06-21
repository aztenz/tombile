package com.n2o.tombile.product.order.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RSPOrderItem {
    private String productName;
    private String description;
    private int quantity;
    private double price;
    private double subtotal;
    private String supplierName;
    private String supplierEmail;
    private String itemStatus;
}

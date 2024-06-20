package com.n2o.tombile.product.order.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RSPCart {
    private String productName;
    private String productDescription;
    private double productPrice;
    private String supplierName;
    private String supplierEmail;
    private int quantity;
    private double subtotal;
}

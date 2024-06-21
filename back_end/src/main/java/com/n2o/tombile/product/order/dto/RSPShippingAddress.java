package com.n2o.tombile.product.order.dto;

import lombok.Getter;

@Getter
public class RSPShippingAddress {
    private int id;
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private String addressType;
}

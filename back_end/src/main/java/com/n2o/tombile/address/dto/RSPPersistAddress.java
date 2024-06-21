package com.n2o.tombile.address.dto;

import lombok.Getter;

@Getter
public class RSPPersistAddress {
    private int id;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private String addressType;
}

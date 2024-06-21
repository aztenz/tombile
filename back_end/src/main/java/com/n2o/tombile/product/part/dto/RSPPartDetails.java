package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.RSPProductDetails;
import lombok.Getter;

@Getter
public class RSPPartDetails extends RSPProductDetails {
    private String manufacturer;
    private String compatibility;
}

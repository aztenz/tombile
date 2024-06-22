package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.RSPPersistProduct;
import lombok.Getter;

@Getter
public class RSPPersistPart extends RSPPersistProduct {
    private String manufacturer;
    private String compatibility;
    private int quantity;
}

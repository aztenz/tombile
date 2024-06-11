package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.ProductListItem;
import lombok.Getter;

@Getter
public class PartListItem extends ProductListItem {
    private String manufacturer;
}

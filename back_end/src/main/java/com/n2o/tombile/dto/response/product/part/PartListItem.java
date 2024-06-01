package com.n2o.tombile.dto.response.product.part;

import com.n2o.tombile.dto.response.product.abs_product.ProductListItem;
import lombok.Getter;

@Getter
public class PartListItem extends ProductListItem {
    private String manufacturer;
}

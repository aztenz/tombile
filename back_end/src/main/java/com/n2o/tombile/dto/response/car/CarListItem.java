package com.n2o.tombile.dto.response.car;

import com.n2o.tombile.enums.CarState;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class CarListItem {
    private int id;
    private String name;
    private double price;
    private CarState carState;
}

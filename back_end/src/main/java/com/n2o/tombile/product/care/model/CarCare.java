package com.n2o.tombile.product.care.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "services")
public class CarCare extends Product {
    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private CarCareType carCareType;
}

package com.n2o.tombile.product.care.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "services")
public class CarCare extends Product {
    private static final String CONTACT_INFO = "contact_info";
    private static final String LOCATION = "location";
    private static final String SERVICE_TYPE = "service_type";

    @Column(name = CONTACT_INFO)
    private String contactInfo;

    @Column(name = LOCATION)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = SERVICE_TYPE)
    private CarCareType carCareType;
}

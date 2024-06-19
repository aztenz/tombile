package com.n2o.tombile.product.care.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Entity
@Table(name = "services", schema = "tombile")
public class CarCare extends Product {
    @Size(max = 100)
    @Column(name = "contact_info", length = 100)
    private String contactInfo;

    @Size(max = 100)
    @Column(name = "location", length = 100)
    private String location;

    @Size(max = 50)
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", length = 50)
    private CarCareType serviceType;
}
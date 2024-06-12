package com.n2o.tombile.address.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Table(name = "addresses")
public class Address {
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String STREET = "street";
    private static final String CITY = "city";
    private static final String ZIP_CODE = "zip_code";
    private static final String COUNTRY = "country";
    private static final String ADDRESS_TYPE = "address_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @ManyToOne
    @JoinColumn(name = USER_ID)
    private User user;

    @Column(name = STREET)
    private String street;

    @Column(name = CITY)
    private String city;

    @Column(name = ZIP_CODE)
    private String zipCode;

    @Column(name = COUNTRY)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = ADDRESS_TYPE)
    private AddressType addressType;
}



package com.n2o.tombile.core.user.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "user_data")
public class UserData {
    private static final String EMAIL = "email";
    private static final String USER_ID = "user_id";
    private static final String ROLE = "role";
    private static final String LAST_LOGIN_DATE = "last_login_date";
    private static final String REGISTRATION_DATE = "registration_date";
    private static final String VERIFICATION_STATUS = "verification_status";
    private static final String WALLET_BALANCE = "wallet_balance";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";

    @Id
    @Column(name = USER_ID)
    private int id;

    @Column(name = EMAIL, unique = true)
    private String email;

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = LAST_NAME)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = ROLE)
    private Role role;

    @Column(name = WALLET_BALANCE)
    private double walletBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = VERIFICATION_STATUS)
    private VerificationStatus verificationStatus;

    @Column(name = REGISTRATION_DATE)
    private Date registrationDate;

    @Column(name = LAST_LOGIN_DATE)
    private Date lastLoginDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = USER_ID)
    private User user;
}
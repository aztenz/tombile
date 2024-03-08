package com.n2o.tombile.model;


import com.n2o.tombile.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_data")
public class UserData {

    private static final String EMAIL = "email";
    private static final String ID = "id";
    private static final String ROLE = "role";
    private static final String LAST_LOGIN_DATE = "last_login_date";
    private static final String REGISTRATION_DATE = "registration_date";
    private static final String VERIFICATION_STATUS = "verification_status";
    private static final String WALLET_BALANCE = "wallet_balance";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = EMAIL, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = ROLE)
    private Role role;

    @Column(name = WALLET_BALANCE)
    private Double walletBalance;

    @Column(name = VERIFICATION_STATUS)
    private Boolean verificationStatus;

    @Column(name = REGISTRATION_DATE)
    private Date registrationDate;

    @Column(name = LAST_LOGIN_DATE)
    private Date lastLoginDate;
}
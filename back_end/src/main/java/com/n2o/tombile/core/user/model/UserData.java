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
    @Id
    @Column(name = "user_id")
    private int id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "wallet_balance")
    private double walletBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus verificationStatus;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
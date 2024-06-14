package com.n2o.tombile.auth.otp.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "otp")
public class Otp {
    @Id
    @Column(name = "user_id")
    private int id;

    @Column(name = "otp_code")
    private int otpCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "otp_type")
    private OtpType otpType;

    @Column(name = "expiration")
    private Date expiration;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}

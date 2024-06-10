package com.n2o.tombile.auth.otp.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "otp")
public class Otp {
    private static final String USER_ID = "user_id";
    private static final String EXPIRATION = "expiration";
    private static final String OTP_TYPE = "otp_type";
    private static final String OTP_CODE = "otp_code";

    @Id
    @Column(name = USER_ID)
    private int id;

    @Column(name = OTP_CODE)
    private int otpCode;

    @Enumerated(EnumType.STRING)
    @Column(name = OTP_TYPE)
    private OtpType otpType;

    @Column(name = EXPIRATION)
    private Date expiration;

    @OneToOne
    @MapsId
    @JoinColumn(name = USER_ID)
    private User user;
}

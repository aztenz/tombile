package com.n2o.tombile.auth.otp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class OtpId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -5444348337226293628L;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Size(max = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "otp_type", nullable = false, length = 50)
    private OtpType otpType;
}
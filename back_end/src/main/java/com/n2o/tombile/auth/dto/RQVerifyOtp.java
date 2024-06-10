package com.n2o.tombile.auth.dto;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class RQVerifyOtp extends RQSendOtp {
    @Valid

    private int otp;
}

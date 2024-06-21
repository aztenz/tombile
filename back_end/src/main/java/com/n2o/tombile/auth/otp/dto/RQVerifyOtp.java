package com.n2o.tombile.auth.otp.dto;

import lombok.Getter;

@Getter
public class RQVerifyOtp extends RQSendOtp {
    private int otp;
}

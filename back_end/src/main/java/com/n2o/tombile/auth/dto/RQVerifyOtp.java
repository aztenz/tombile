package com.n2o.tombile.auth.dto;

import lombok.Getter;

@Getter
public class RQVerifyOtp extends RQSendOtp {
    private int otp;
}

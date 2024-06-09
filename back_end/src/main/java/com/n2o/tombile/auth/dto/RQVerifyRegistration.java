package com.n2o.tombile.auth.dto;

import lombok.Getter;

@Getter
public class RQVerifyRegistration extends RQSendOtp {
    private int otp;
}

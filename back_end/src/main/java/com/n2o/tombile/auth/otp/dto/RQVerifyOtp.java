package com.n2o.tombile.auth.otp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RQVerifyOtp extends RQSendOtp {
    private int otp;
}

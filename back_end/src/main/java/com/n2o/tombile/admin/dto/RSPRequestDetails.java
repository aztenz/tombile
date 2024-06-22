package com.n2o.tombile.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class RSPRequestDetails extends RSPRequestListItem {
    private String username;
    private Instant registrationDate;
    private double walletBalance;
}

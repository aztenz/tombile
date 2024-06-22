package com.n2o.tombile.core.user.dto;

import com.n2o.tombile.address.dto.RSPAddressListItem;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class RSPUserProfile {
    private String firstName;
    private String lastName;
    private String email;
    private double walletBalance;
    private Instant registrationDate;
    private String username;
    private List<RSPAddressListItem> addresses;
}

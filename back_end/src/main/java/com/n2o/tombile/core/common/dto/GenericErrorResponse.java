package com.n2o.tombile.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}

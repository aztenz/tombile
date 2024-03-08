package com.n2o.tombile.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}

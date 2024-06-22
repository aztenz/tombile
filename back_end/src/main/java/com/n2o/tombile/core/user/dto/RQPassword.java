package com.n2o.tombile.core.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_NEW_PASSWORD_NOT_EMPTY;
import static com.n2o.tombile.core.common.util.Constants.ERROR_OLD_PASSWORD_NOT_EMPTY;

@Getter
public class RQPassword {
    @NotEmpty(message = ERROR_OLD_PASSWORD_NOT_EMPTY)
    private String oldPassword;

    @NotEmpty(message = ERROR_NEW_PASSWORD_NOT_EMPTY)
    private String newPassword;
}

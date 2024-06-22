package com.n2o.tombile.core.user.dto;

import com.n2o.tombile.core.common.validate.NotEmptyIfPresent;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_FIRST_NAME_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_LAST_NAME_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USERNAME_REQUIRED;

@Getter
public class RQProfile {
    @NotEmptyIfPresent(message = ERROR_FIRST_NAME_REQUIRED)
    private String firstName;

    @NotEmptyIfPresent(message = ERROR_LAST_NAME_REQUIRED)
    private String lastName;

    @NotEmptyIfPresent(message = ERROR_USERNAME_REQUIRED)
    private String username;
}

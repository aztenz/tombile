package com.n2o.tombile.dto.request.product;

import com.n2o.tombile.model.CarCareType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PersistCarCareRQ extends PersistProductRQ {
    private static final String LOCATION_IS_MANDATORY = "location is mandatory";
    private static final String CONTACT_INFO_IS_MANDATORY = "contact info is mandatory";
    private static final String SERVICE_TYPE_IS_MANDATORY = "service type is mandatory";
    @Valid

    @NotNull(message = CONTACT_INFO_IS_MANDATORY)
    @NotEmpty(message = CONTACT_INFO_IS_MANDATORY)
    private String contactInfo;

    @NotNull(message = LOCATION_IS_MANDATORY)
    @NotEmpty(message = LOCATION_IS_MANDATORY)
    private String location;

    @NotNull(message = SERVICE_TYPE_IS_MANDATORY)
    private CarCareType carCareType;
}

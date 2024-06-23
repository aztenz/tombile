package com.n2o.tombile.product.product.dto;

import com.n2o.tombile.core.common.validate.annotation.ImageFile;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PRICE_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NUM_MIN;

@Getter
@Setter
public abstract class RQPersistProduct {
    private String description;

    @Min(value = POSITIVE_NUM_MIN, message = ERROR_PRICE_NEGATIVE)
    private double price;

    @ImageFile
    private MultipartFile image;
}

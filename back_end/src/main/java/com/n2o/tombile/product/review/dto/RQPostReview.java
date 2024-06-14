package com.n2o.tombile.product.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_COMMENT_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_REVIEW_TOO_HIGH;
import static com.n2o.tombile.core.common.util.Constants.ERROR_REVIEW_TOO_LOW;
import static com.n2o.tombile.core.common.util.Constants.RATING_MAX_VALUE;
import static com.n2o.tombile.core.common.util.Constants.RATING_MIN_VALUE;

@Getter
public class RQPostReview {
    @Min(value = RATING_MIN_VALUE, message = ERROR_REVIEW_TOO_LOW)
    @Max(value = RATING_MAX_VALUE, message = ERROR_REVIEW_TOO_HIGH)
    private int rating;

    @NotEmpty(message = ERROR_COMMENT_REQUIRED)
    private String comment;
}

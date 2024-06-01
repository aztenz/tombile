package com.n2o.tombile.dto.request.review;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostReviewRQ {
    private static final String MIN_REVIEW_CONSTRAINT = "review can't be less than 1";
    private static final String MAX_REVIEW_CONSTRAINT = "review can't exceed 5";
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;
    public static final String COMMENT_IS_MANDATORY = "comment is mandatory";

    @Valid

    @Min(value = MIN_RATING, message = MIN_REVIEW_CONSTRAINT)
    @Max(value = MAX_RATING, message = MAX_REVIEW_CONSTRAINT)
    private int rating;

    @NotNull(message = COMMENT_IS_MANDATORY)
    @NotEmpty(message = COMMENT_IS_MANDATORY)
    private String comment;
}

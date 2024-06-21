package com.n2o.tombile.product.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RSPReviewDetails {
    private int rating;
    private String comment;
    private Date reviewDate;
    private String userFirstName;
    private String userLastName;
}

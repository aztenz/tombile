package com.n2o.tombile.dto.response.review;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReviewDetails {
    private int rating;
    private String comment;
    private Date reviewDate;
    private String userFirstName;
    private String userLastName;
}

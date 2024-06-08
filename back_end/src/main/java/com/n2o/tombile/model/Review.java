package com.n2o.tombile.model;

import com.n2o.tombile.auth.model.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String REVIEW_DATE = "review_date";
    private static final String PRODUCT_ID = "product_id";
    private static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = RATING)
    private int rating;

    @Column(name = COMMENT)
    private String comment;

    @Column(name = REVIEW_DATE)
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = PRODUCT_ID)
    private Product product;

    @ManyToOne
    @JoinColumn(name = USER_ID)
    private User user;
}

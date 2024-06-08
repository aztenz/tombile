package com.n2o.tombile.auth.model.entity;

import com.n2o.tombile.auth.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Table(name = "tokens")
public class Token {
    private static final String TOKEN = "token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String USER_ID = "user_id";

    @Id
    @Column(name = USER_ID)
    private int id;

    @Column(name = TOKEN, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = TOKEN_TYPE)
    private TokenType tokenType;

    @OneToOne
    @MapsId
    @JoinColumn(name = USER_ID)
    private User user;
}

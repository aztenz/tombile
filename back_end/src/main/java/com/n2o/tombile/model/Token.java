package com.n2o.tombile.model;

import com.n2o.tombile.enums.TokenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tokens")
public class Token {
    private static final String ID = "id";
    private static final String TOKEN = "token";
    private static final String TOKEN_TYPE = "token_type";
    private static final String EXPIRED = "expired";
    private static final String REVOKED = "revoked";
    private static final String USER_ID = "user_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = TOKEN, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = TOKEN_TYPE)
    private TokenType tokenType;

    @Column(name = EXPIRED)
    private boolean expired;

    @Column(name = REVOKED)
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = USER_ID)
    private User user;
}

package com.n2o.tombile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean expired;

    @Column(name = REVOKED)
    private Boolean revoked;

    @ManyToOne
    @JoinColumn(name = USER_ID)
    private User user;
    
    public enum TokenType {
        BEARER
    }
}

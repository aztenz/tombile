package com.n2o.tombile.auth.token.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Setter;

@Setter
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @Column(name = "user_id")
    private int id;

    @Column(name = "token", unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}

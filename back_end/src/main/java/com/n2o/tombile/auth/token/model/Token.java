package com.n2o.tombile.auth.token.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tokens", schema = "tombile",
        uniqueConstraints = @UniqueConstraint(name = "token", columnNames = {"token"}))
public class Token {
    @Id
    @Column(name = "user_id", nullable = false)
    private int id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 256)
    @Column(name = "token", length = 256)
    private String token;

    @Size(max = 50)
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", length = 50)
    private TokenType tokenType;
}

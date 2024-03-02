package com.n2o.tombile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.n2o.tombile.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
    
    @Query("SELECT t " +
            "FROM Token t " +
            "INNER JOIN User u " +
            "ON t.user.id = u.id " +
            "WHERE u.id = :userId " +
            "AND (t.expired = FALSE OR t.revoked = FALSE)")
    List<Token> findAllValidTokensByUser(int userId);
    Optional<Token> findByToken(String token);
}
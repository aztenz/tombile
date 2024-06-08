package com.n2o.tombile.auth.repository;

import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.auth.model.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("FROM User u WHERE u.userData.email = :email AND u.userData.verificationStatus = :vs")
    Optional<User> findByEmailAndVerificationStatus(String email, VerificationStatus vs);
}
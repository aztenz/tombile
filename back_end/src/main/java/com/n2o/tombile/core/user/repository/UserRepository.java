package com.n2o.tombile.core.user.repository;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("FROM User u WHERE u.userData.email = :email")
    Optional<User> findByEmail(String email);

    @Query("FROM User u WHERE u.username = :username OR u.userData.email = :email")
    Optional<User> findByUsernameOrEmail(String username, String email);

    @Query("FROM User u WHERE u.username = :username AND u.userData.verificationStatus = :vs")
    Optional<User> findByUsernameAndVerificationStatus(String username, VerificationStatus vs);

    @Query("FROM User u WHERE u.userData.email = :email AND u.userData.verificationStatus = :vs")
    Optional<User> findByEmailAndVerificationStatus(String email, VerificationStatus vs);

    @Query("FROM User u JOIN UserData ud ON ud.user = u WHERE ud.verificationStatus = :vs")
    List<User> findAllByVerificationStatus(VerificationStatus vs);

    @Query("FROM User u JOIN UserData ud ON ud.user = u WHERE ud.verificationStatus = :vs AND u.id = :userId")
    Optional<User> findByIdAndVerificationStatus(VerificationStatus vs, int userId);
}
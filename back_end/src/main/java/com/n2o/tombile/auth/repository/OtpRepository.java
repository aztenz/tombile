package com.n2o.tombile.auth.repository;

import com.n2o.tombile.auth.model.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
}

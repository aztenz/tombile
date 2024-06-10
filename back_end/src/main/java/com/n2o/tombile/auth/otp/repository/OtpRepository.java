package com.n2o.tombile.auth.otp.repository;

import com.n2o.tombile.auth.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Integer> {
}

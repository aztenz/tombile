package com.n2o.tombile.auth.otp.repository;

import com.n2o.tombile.auth.otp.model.Otp;
import com.n2o.tombile.auth.otp.model.OtpId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, OtpId> {
}

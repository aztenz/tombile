package com.n2o.tombile.auth.service;

import com.n2o.tombile.dto.MailBody;
import com.n2o.tombile.enums.OtpType;
import com.n2o.tombile.exception.InvalidOtpException;
import com.n2o.tombile.auth.model.entity.Otp;
import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.auth.repository.OtpRepository;
import com.n2o.tombile.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private static final String INVALID_OTP = "invalid otp";
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    @Value("${otp.range.min}")
    private static int minOtpRange;

    @Value("${otp.range.max}")
    private static int maxOtpRange;

    @Value("${otp.expiration.duration}")
    private static int otpExpirationDuration;

    public void sendOtpForVerification(User user, OtpType otpType) {
        revokeExistingOtp(user.getId());

        Otp otp = createOtp(user, otpType);

        MailBody mailBody = createMailBody(user, otp);

        otpRepository.save(otp);

        emailService.sendSimpleMessage(mailBody);
    }

    public void verifyOtp(int requestedOtp, User user) {
        Otp otp = otpRepository.findById(user.getId())
                .orElseThrow(() -> new InvalidOtpException(INVALID_OTP));

        boolean isOtpNotMatch = otp.getOtpCode() != requestedOtp;
        boolean isOtpExpired = otp.getExpiration().after(Date.from(Instant.now()));

        if(isOtpNotMatch || isOtpExpired)
            throw new InvalidOtpException(INVALID_OTP);
    }

    private MailBody createMailBody(User user, Otp otp) {
        return MailBody.builder()
                .to(user.getUserData().getEmail())
                .subject(otp.getOtpType().getSubject())
                .text(prepareEmailBody(user, otp))
                .build();
    }

    private String prepareEmailBody(User user, Otp otp) {
        return MessageFormat.format(
                otp.getOtpType().getBody(),
                user.getUserData().getFirstName(),
                otp.getOtpCode());
    }

    private Otp createOtp(User user, OtpType otpType) {
        Otp otp = new Otp();
        otp.setOtpCode(generateOtp());
        otp.setExpiration(new Date(System.currentTimeMillis() + otpExpirationDuration));
        otp.setOtpType(otpType);
        otp.setUser(user);
        return otp;
    }

    private void revokeExistingOtp(int id) {
        otpRepository.findById(id).ifPresent(otpRepository::delete);
    }

    private int generateOtp() {
        Random random = new Random();
        return random.nextInt(minOtpRange, maxOtpRange);
    }
}
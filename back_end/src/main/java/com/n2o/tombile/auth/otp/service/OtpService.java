package com.n2o.tombile.auth.otp.service;

import com.n2o.tombile.auth.otp.model.Otp;
import com.n2o.tombile.auth.otp.model.OtpId;
import com.n2o.tombile.auth.otp.model.OtpType;
import com.n2o.tombile.auth.otp.repository.OtpRepository;
import com.n2o.tombile.core.common.exception.InvalidOtpException;
import com.n2o.tombile.core.mail.EmailService;
import com.n2o.tombile.core.mail.MailBody;
import com.n2o.tombile.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Random;

import static com.n2o.tombile.core.common.util.Constants.ERROR_OTP_EXPIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_OTP_INVALID;
import static com.n2o.tombile.core.common.util.Constants.ERROR_OTP_TYPE_MISMATCH;
import static com.n2o.tombile.core.common.util.Constants.OTP_EXPIRATION_MILLISECONDS;
import static com.n2o.tombile.core.common.util.Constants.OTP_MAX_VALUE;
import static com.n2o.tombile.core.common.util.Constants.OTP_MIN_VALUE;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public void sendOtpForVerification(User user, OtpType otpType) {
        OtpId otpId = getOtpId(user.getId(), otpType);
        revokeExistingOtp(otpId);

        Otp otp = createOtp(user, otpType);

        MailBody mailBody = createMailBody(user, otp);

        otpRepository.save(otp);

        emailService.sendSimpleMessage(mailBody);
    }

    public void verifyOtp(int requestedOtp, User user, OtpType otpType) {
        OtpId otpId = getOtpId(user.getId(), otpType);
        Otp otp = otpRepository.findById(otpId)
                .orElseThrow(() -> new InvalidOtpException(ERROR_OTP_INVALID));

        boolean isOtpNotMatch = otp.getOtpCode() != requestedOtp;
        boolean isOtpTypeNotMatch = otp.getId().getOtpType() != otpType;
        boolean isOtpExpired = otp.getExpiration().isBefore(Instant.now());

        if(isOtpNotMatch)
            throw new InvalidOtpException(ERROR_OTP_INVALID);
        if(isOtpExpired)
            throw new InvalidOtpException(ERROR_OTP_EXPIRED);
        if(isOtpTypeNotMatch)
            throw new InvalidOtpException(ERROR_OTP_TYPE_MISMATCH);
    }

    private MailBody createMailBody(User user, Otp otp) {
        return MailBody.builder()
                .to(user.getUserData().getEmail())
                .subject(otp.getId().getOtpType().getSubject())
                .text(prepareEmailBody(user, otp))
                .build();
    }

    private String prepareEmailBody(User user, Otp otp) {
        return MessageFormat.format(
                otp.getId().getOtpType().getBody(),
                user.getUserData().getFirstName(),
                otp.getOtpCode());
    }

    private Otp createOtp(User user, OtpType otpType) {
        OtpId otpId = getOtpId(user.getId(), otpType);
        Otp otp = new Otp();
        otp.setOtpCode(generateOtp());
        otp.setExpiration(Instant.ofEpochMilli(Instant.now().toEpochMilli() + OTP_EXPIRATION_MILLISECONDS));
        otp.setId(otpId);
        otp.setUser(user);
        return otp;
    }

    private void revokeExistingOtp(OtpId otpId) {
        otpRepository.findById(otpId).ifPresent(otpRepository::delete);
    }

    private int generateOtp() {
        Random random = new Random();
        return random.nextInt(OTP_MIN_VALUE, OTP_MAX_VALUE);
    }

    private OtpId getOtpId(int userId, OtpType otpType) {
        OtpId otpId = new OtpId();
        otpId.setOtpType(otpType);
        otpId.setUserId(userId);
        return otpId;
    }
}

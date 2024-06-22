package com.n2o.tombile.core.user.controller;

import com.n2o.tombile.auth.otp.dto.RQVerifyOtp;
import com.n2o.tombile.auth.otp.model.OtpType;
import com.n2o.tombile.auth.otp.service.OtpStrategyFactory;
import com.n2o.tombile.core.user.dto.RQCharge;
import com.n2o.tombile.core.user.dto.RQEmail;
import com.n2o.tombile.core.user.dto.RQProfile;
import com.n2o.tombile.core.user.dto.RSPUserProfile;
import com.n2o.tombile.core.user.service.UserService;
import com.n2o.tombile.core.user.dto.RQPassword;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OtpStrategyFactory otpStrategyFactory;

    @GetMapping
    public ResponseEntity<RSPUserProfile> getUserProfile() {
        return ResponseEntity.ok(userService.getUserProfile());
    }

    @PutMapping
    public ResponseEntity<String> updateUserProfile(@RequestBody @Valid RQProfile request) {
        return ResponseEntity.ok(userService.modifyProfile(request));
    }

    @PostMapping("/charge")
    public ResponseEntity<String> chargeUser(@RequestBody @Valid RQCharge request) {
        return ResponseEntity.ok(userService.chargeAccount(request));
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid RQPassword request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }

    @PutMapping("/email")
    public ResponseEntity<String> changeEmail(@RequestBody @Valid RQEmail request) {
        return ResponseEntity.ok(otpStrategyFactory
                .getStrategy(OtpType.CHANGE_EMAIL).sendOtp(request.getEmail(), OtpType.CHANGE_EMAIL));
    }

    @PutMapping("/email/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam int otp) {
        RQVerifyOtp request = new RQVerifyOtp();
        request.setOtp(otp);
        request.setEmail("email");
        return ResponseEntity.ok(otpStrategyFactory
                .getStrategy(OtpType.CHANGE_EMAIL)
                .verifyOtp(request));
    }

}

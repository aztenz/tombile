package com.n2o.tombile.auth.controller;

import com.n2o.tombile.auth.dto.*;
import com.n2o.tombile.auth.service.AuthenticationService;
import com.n2o.tombile.enums.OtpType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RQRegister request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<RSPToken> login(
            @Valid @RequestBody RQLogin request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(
            @Valid @RequestBody RQVerifyOtp request
    ) {
        return ResponseEntity.ok(authenticationService.verifyOtp(request, OtpType.VERIFY_EMAIL));
    }

    @PostMapping("/resend-confirm-email")
    public ResponseEntity<String> resendConfirmEmail(
            @Valid @RequestBody RQSendOtp request
    ) {
        return ResponseEntity.ok(authenticationService.sendOtp(request, OtpType.VERIFY_EMAIL));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(
            @Valid @RequestBody RQSendOtp request
    ) {
        return ResponseEntity.ok(authenticationService.sendOtp(request, OtpType.RECOVER_PASSWORD));
    }

    @PostMapping("/verify-forget-password")
    public ResponseEntity<String> verifyForgetPassword(
            @Valid @RequestBody RQVerifyOtp request
    ) {
        return ResponseEntity.ok(authenticationService.verifyOtp(request, OtpType.RECOVER_PASSWORD));
    }

    @PostMapping("/resend-forget-password")
    public ResponseEntity<String> resendForgetPassword(
            @Valid @RequestBody RQSendOtp request
    ) {
        return ResponseEntity.ok(authenticationService.sendOtp(request, OtpType.RECOVER_PASSWORD));
    }
}

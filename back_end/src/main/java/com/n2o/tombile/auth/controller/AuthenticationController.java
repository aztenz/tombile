package com.n2o.tombile.auth.controller;

import com.n2o.tombile.auth.dto.RQLogin;
import com.n2o.tombile.auth.dto.RQRegister;
import com.n2o.tombile.auth.dto.RQVerifyEmail;
import com.n2o.tombile.auth.dto.RSPToken;
import com.n2o.tombile.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
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

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @Valid @RequestBody RQVerifyEmail request
    ) {
        return ResponseEntity.ok(authenticationService.verifyEmail(request));
    }

}

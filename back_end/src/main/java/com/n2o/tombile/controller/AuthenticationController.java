package com.n2o.tombile.controller;

import com.n2o.tombile.dto.response.auth.AuthenticationDTO;
import com.n2o.tombile.dto.request.auth.LoginUserDTO;
import com.n2o.tombile.dto.request.auth.RegisterUserDTO;
import com.n2o.tombile.service.AuthenticationService;
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
    public ResponseEntity<AuthenticationDTO> register(
            @Valid @RequestBody RegisterUserDTO request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(
            @Valid @RequestBody LoginUserDTO loginUserDTO
    ) {
        return ResponseEntity.ok(authenticationService.login(loginUserDTO));
    }

}

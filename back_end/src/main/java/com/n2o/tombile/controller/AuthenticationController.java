package com.n2o.tombile.controller;

import com.n2o.tombile.dto.auth.LoginUserDTO;
import com.n2o.tombile.dto.auth.AuthenticationDTO;
import com.n2o.tombile.dto.auth.RegisterUserDTO;
import com.n2o.tombile.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDTO));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

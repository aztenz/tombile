package com.n2o.tombile.core.common.aspect;

import com.n2o.tombile.core.common.exception.InvalidOtpException;
import com.n2o.tombile.core.common.dto.GenericErrorResponse;
import com.n2o.tombile.core.common.exception.DuplicateItemException;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    public static final String ERROR_HANDLING_REQUEST = "Error handling request";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> duplicateItem(
            DuplicateItemException e
    ) {
        return handleGenericException(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> itemNotFound(
            ItemNotFoundException e
    ) {
        return handleGenericException(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> badCredentials(
            BadCredentialsException e
    ) {
        return handleGenericException(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> invalidOtp(
            InvalidOtpException e
    ) {
        return handleGenericException(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> genericException(
            Exception e
    ) {
        return handleGenericException(ERROR_HANDLING_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<GenericErrorResponse> handleGenericException(
            String message,
            HttpStatus httpStatus
    ) {
        GenericErrorResponse err = new GenericErrorResponse(
                httpStatus.value(),
                message,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(err, httpStatus);
    }
}
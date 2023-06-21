package com.example.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidExceptionViolation(MethodArgumentNotValidException exception) {
        final var responseBody =
                "Validation failed on fields: " + exception.getFieldErrors().stream().map(FieldError::getField).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

}

package com.eventiq.identity.exception;

import com.eventiq.identity.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().msg(exception.getLocalizedMessage()).build());
    }
}

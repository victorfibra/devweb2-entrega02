package com.example.entrega02.controller;

import com.example.entrega02.exception.ContratoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ContratoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleContratoNotFound(ContratoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}

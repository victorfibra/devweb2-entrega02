package com.example.entrega02.exception;

public class ContratoNotFoundException extends RuntimeException {

    public ContratoNotFoundException(Long id) {
        super("Contrato não encontrado com id: " + id);
    }
}

package com.example.entrega02.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicoAdicionalDTO(
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que 0")
        Double valor
) {}

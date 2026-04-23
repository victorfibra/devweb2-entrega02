package com.example.entrega02.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanoDTO(

    Long id,

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que 0")
    Double preco,

    @NotNull(message = "Velocidade é obrigatória")
    @DecimalMin(value = "1.0", message = "Velocidade deve ser maior que 0")
    Double velocidadeMbps
) {}
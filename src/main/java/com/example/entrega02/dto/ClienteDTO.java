package com.example.entrega02.dto;

import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(

    Long id,

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @NotBlank(message = "CPF é obrigatório")
    String cpf,

    @NotBlank(message = "Email é obrigatório")
    String email
) {}
package com.example.entrega02.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record ContratoRequestDTO(

        @NotNull(message = "Data de ativação é obrigatória")
        LocalDate dataAtivacao,

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Plano é obrigatório")
        Long planoId,

        @NotNull(message = "Lista de serviços não pode ser nula")
        @NotEmpty(message = "Lista de serviços não pode estar vazia")
        @Size(min = 1, message = "É necessário informar ao menos um serviço adicional")
        List<Long> servicosIds
) {
}

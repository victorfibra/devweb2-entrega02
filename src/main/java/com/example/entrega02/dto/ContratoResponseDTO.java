package com.example.entrega02.dto;

import java.time.LocalDate;
import java.util.List;

public record ContratoResponseDTO(
        Long id,
        LocalDate dataAtivacao,
        Long clienteId,
        Long planoId,
        List<Long> servicosIds
) {
}

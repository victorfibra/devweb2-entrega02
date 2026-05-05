package com.example.entrega02.controller;

import com.example.entrega02.dto.ServicoAdicionalDTO;
import com.example.entrega02.model.ServicoAdicional;
import com.example.entrega02.repository.ServicoAdicionalRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoAdicionalController {

    @Autowired
    private ServicoAdicionalRepository repository;

    @GetMapping
    public List<ServicoAdicionalDTO> listar() {
        return repository.findAll().stream()
                .map(servico -> new ServicoAdicionalDTO(
                        servico.getId(),
                        servico.getNome(),
                        servico.getValor()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<ServicoAdicionalDTO> criar(@RequestBody @Valid ServicoAdicionalDTO dto) {
        ServicoAdicional servico = new ServicoAdicional();
        servico.setNome(dto.nome());
        servico.setValor(dto.valor());

        ServicoAdicional salvo = repository.save(servico);

        ServicoAdicionalDTO response = new ServicoAdicionalDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getValor()
        );
        
        URI location = URI.create(String.format("/api/servicos/%d", salvo.getId()));
        return ResponseEntity.created(location).body(response);
    }
}
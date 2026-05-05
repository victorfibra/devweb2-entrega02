package com.example.entrega02.controller;

import com.example.entrega02.dto.PlanoDTO;
import com.example.entrega02.model.Plano;
import com.example.entrega02.repository.PlanoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    @Autowired
    private PlanoRepository repository;

    @GetMapping
    public List<PlanoDTO> listar() {
        return repository.findAll().stream()
                .map(plano -> new PlanoDTO(
                        plano.getId(),
                        plano.getNome(),
                        plano.getPreco(),
                        plano.getVelocidadeMbps()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<PlanoDTO> salvar(@RequestBody @Valid PlanoDTO dto) {

        Plano plano = new Plano();
        plano.setNome(dto.nome());
        plano.setPreco(dto.preco());
        plano.setVelocidadeMbps(dto.velocidadeMbps());

        Plano salvo = repository.save(plano);

        PlanoDTO response = new PlanoDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getPreco(),
                salvo.getVelocidadeMbps()
        );
        
        URI location = URI.create(String.format("/api/planos/%d", salvo.getId()));
        return ResponseEntity.created(location).body(response);
    }
}
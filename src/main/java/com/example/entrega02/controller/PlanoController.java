package com.example.entrega02.controller;

import com.example.entrega02.dto.PlanoDTO;
import com.example.entrega02.model.Plano;
import com.example.entrega02.repository.PlanoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private PlanoRepository repository;

    @GetMapping
    public List<Plano> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PlanoDTO salvar(@RequestBody @Valid PlanoDTO dto) {

        Plano plano = new Plano();
        plano.setNome(dto.nome());
        plano.setPreco(dto.preco());
        plano.setVelocidadeMbps(dto.velocidadeMbps());

        Plano salvo = repository.save(plano);

        return new PlanoDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getPreco(),
                salvo.getVelocidadeMbps()
        );
    }
}
package com.example.entrega02.controller;

import com.example.entrega02.dto.ClienteDTO;
import com.example.entrega02.model.Cliente;
import com.example.entrega02.repository.ClienteRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // GET
    @GetMapping
    public List<ClienteDTO> listar() {
        return repository.findAll().stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
                        cliente.getNome()
                ))
                .toList();
    }

    // POST
    @PostMapping
    public ClienteDTO salvar(@RequestBody @Valid ClienteDTO dto) {

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());

        Cliente salvo = repository.save(cliente);

        return new ClienteDTO(
                salvo.getId(),
                salvo.getNome()
        );
    }
}
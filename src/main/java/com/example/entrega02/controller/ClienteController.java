package com.example.entrega02.controller;

import com.example.entrega02.dto.ClienteDTO;
import com.example.entrega02.model.Cliente;
import com.example.entrega02.repository.ClienteRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    // GET
    @GetMapping
    public List<ClienteDTO> listar() {
        return repository.findAll().stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getEmail()
                ))
                .toList();
    }

    // POST
    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@RequestBody @Valid ClienteDTO dto) {

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());

        Cliente salvo = repository.save(cliente);

        ClienteDTO response = new ClienteDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCpf(),
                salvo.getEmail()
        );
        
        URI location = URI.create(String.format("/api/clientes/%d", salvo.getId()));
        return ResponseEntity.created(location).body(response);
    }
}
package com.example.entrega02.controller;

import com.example.entrega02.model.ServicoAdicional;
import com.example.entrega02.repository.ServicoAdicionalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoAdicionalController {

    @Autowired
    private ServicoAdicionalRepository repository;

    @GetMapping
    public List<ServicoAdicional> listar() {
        return repository.findAll();
    }

    @PostMapping
    public ServicoAdicional criar(@RequestBody ServicoAdicional servico) {
        return repository.save(servico);
    }
}
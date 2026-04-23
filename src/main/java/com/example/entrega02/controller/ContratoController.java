package com.example.entrega02.controller;

import com.example.entrega02.dto.ContratoDTO;
import com.example.entrega02.model.*;
import com.example.entrega02.repository.*;
import com.example.entrega02.service.ContratoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoService service;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private ServicoAdicionalRepository servicoRepository;

    // GET (DTO obrigatório)
    @GetMapping
    public List<ContratoDTO> listar() {
        return service.listar().stream()
                .map(c -> new ContratoDTO(
                        c.getDataAtivacao(),
                        c.getCliente().getId(),
                        c.getPlano().getId(),
                        c.getServicos().stream()
                                .map(ServicoAdicional::getId)
                                .toList()
                ))
                .toList();
    }

    // POST (DTO entrada + DTO saída ✔ agora correto)
    @PostMapping
    public ContratoDTO criar(@RequestBody @Valid ContratoDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Plano plano = planoRepository.findById(dto.planoId())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        List<ServicoAdicional> servicos =
                servicoRepository.findAllById(dto.servicosIds());

        ContratoInternet contrato = new ContratoInternet();
        contrato.setDataAtivacao(dto.dataAtivacao());
        contrato.setCliente(cliente);
        contrato.setPlano(plano);
        contrato.setServicos(servicos);

        ContratoInternet salvo = service.salvar(contrato);

        // RETORNO PADRONIZADO EM DTO
        return new ContratoDTO(
                salvo.getDataAtivacao(),
                salvo.getCliente().getId(),
                salvo.getPlano().getId(),
                salvo.getServicos().stream()
                        .map(ServicoAdicional::getId)
                        .toList()
        );
    }

    // NATIVE QUERY
    @GetMapping("/nativo")
    public List<ContratoInternet> listarNativo() {
        return service.listarNativo();
    }
}
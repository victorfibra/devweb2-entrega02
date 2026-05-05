package com.example.entrega02.controller;

import com.example.entrega02.dto.ContratoRequestDTO;
import com.example.entrega02.dto.ContratoResponseDTO;
import com.example.entrega02.model.Cliente;
import com.example.entrega02.model.ContratoInternet;
import com.example.entrega02.model.Plano;
import com.example.entrega02.model.ServicoAdicional;
import com.example.entrega02.repository.ClienteRepository;
import com.example.entrega02.repository.PlanoRepository;
import com.example.entrega02.repository.ServicoAdicionalRepository;
import com.example.entrega02.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService service;
    private final ClienteRepository clienteRepository;
    private final PlanoRepository planoRepository;
    private final ServicoAdicionalRepository servicoRepository;

    public ContratoController(ContratoService service,
                              ClienteRepository clienteRepository,
                              PlanoRepository planoRepository,
                              ServicoAdicionalRepository servicoRepository) {
        this.service = service;
        this.clienteRepository = clienteRepository;
        this.planoRepository = planoRepository;
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','TECNICO','CLIENTE')")
    public List<ContratoResponseDTO> listar() {
        return service.listar();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContratoResponseDTO> criar(@RequestBody @Valid ContratoRequestDTO dto) {
        ContratoInternet contrato = buildContratoFrom(dto);
        ContratoInternet salvo = service.salvar(contrato);
        ContratoResponseDTO response = toResponse(salvo);
        URI location = URI.create(String.format("/api/contratos/%d", salvo.getId()));
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
    public ContratoResponseDTO atualizar(@PathVariable Long id,
                                         @RequestBody @Valid ContratoRequestDTO dto) {
        ContratoInternet contratoAtualizado = buildContratoFrom(dto);
        ContratoInternet salvo = service.atualizar(id, contratoAtualizado);
        return toResponse(salvo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.deletar(id);
    }

    @GetMapping("/nativo")
    @PreAuthorize("hasAnyRole('ADMIN','TECNICO','CLIENTE')")
    public List<ContratoResponseDTO> listarNativo() {
        return service.listarNativo();
    }

    private ContratoInternet buildContratoFrom(ContratoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Plano plano = planoRepository.findById(dto.planoId())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        List<ServicoAdicional> servicos = servicoRepository.findAllById(dto.servicosIds());
        if (servicos.size() != dto.servicosIds().size()) {
            throw new RuntimeException("Um ou mais serviços adicionais não foram encontrados");
        }

        ContratoInternet contrato = new ContratoInternet();
        contrato.setDataAtivacao(dto.dataAtivacao());
        contrato.setCliente(cliente);
        contrato.setPlano(plano);
        contrato.setServicos(servicos);
        return contrato;
    }

    private ContratoResponseDTO toResponse(ContratoInternet contrato) {
        return new ContratoResponseDTO(
                contrato.getId(),
                contrato.getDataAtivacao(),
                contrato.getCliente().getId(),
                contrato.getPlano().getId(),
                contrato.getServicos().stream().map(ServicoAdicional::getId).toList()
        );
    }
}

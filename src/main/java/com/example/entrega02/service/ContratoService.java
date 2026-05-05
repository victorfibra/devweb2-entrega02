package com.example.entrega02.service;

import com.example.entrega02.dto.ContratoResponseDTO;
import com.example.entrega02.exception.ContratoNotFoundException;
import com.example.entrega02.model.ContratoInternet;
import com.example.entrega02.model.ServicoAdicional;
import com.example.entrega02.model.audit.LogAuditoria;
import com.example.entrega02.repository.ContratoRepository;
import com.example.entrega02.repository.audit.LogAuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final LogAuditoriaRepository logRepository;

    public ContratoService(ContratoRepository contratoRepository, LogAuditoriaRepository logRepository) {
        this.contratoRepository = contratoRepository;
        this.logRepository = logRepository;
    }

    @Transactional
    public ContratoInternet salvar(ContratoInternet contrato) {
        ContratoInternet salvo = contratoRepository.save(contrato);
        criarLog("Contrato criado com ID: " + salvo.getId());
        return salvo;
    }

    @Transactional(readOnly = true)
    public List<ContratoResponseDTO> listar() {
        return contratoRepository.buscarComCliente().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ContratoResponseDTO> listarNativo() {
        return contratoRepository.buscarTodosNativo().stream()
                .map(this::toDTO)
                .toList();
    }

    public ContratoInternet buscarPorId(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new ContratoNotFoundException(id));
    }

    @Transactional
    public ContratoInternet atualizar(Long id, ContratoInternet contratoAtualizado) {
        ContratoInternet existente = buscarPorId(id);
        existente.setDataAtivacao(contratoAtualizado.getDataAtivacao());
        existente.setCliente(contratoAtualizado.getCliente());
        existente.setPlano(contratoAtualizado.getPlano());
        existente.setServicos(contratoAtualizado.getServicos());

        ContratoInternet salvo = contratoRepository.save(existente);
        criarLog("Contrato atualizado com ID: " + salvo.getId());
        return salvo;
    }

    @Transactional
    public void deletar(Long id) {
        ContratoInternet contrato = buscarPorId(id);
        contratoRepository.delete(contrato);
        criarLog("Contrato excluído com ID: " + id);
    }

    @Transactional("auditTransactionManager")
    public void criarLog(String mensagem) {
        LogAuditoria log = new LogAuditoria();
        log.setMensagem(mensagem);
        log.setDataHora(LocalDateTime.now());
        logRepository.save(log);
    }
    
    private ContratoResponseDTO toDTO(ContratoInternet contrato) {
        return new ContratoResponseDTO(
            contrato.getId(),
            contrato.getDataAtivacao(),
            contrato.getCliente() != null ? contrato.getCliente().getId() : null,
            contrato.getPlano() != null ? contrato.getPlano().getId() : null,
            contrato.getServicos() != null
                    ? contrato.getServicos().stream().map(ServicoAdicional::getId).toList()
                    : java.util.List.of()
        );
    }
}

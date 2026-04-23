package com.example.entrega02.service;

import com.example.entrega02.model.ContratoInternet;
import com.example.entrega02.model.audit.LogAuditoria;
import com.example.entrega02.repository.ContratoRepository;
import com.example.entrega02.repository.audit.LogAuditoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private LogAuditoriaRepository logRepository;

    // CREATE + LOG AUDITORIA
    @Transactional
    public ContratoInternet salvar(ContratoInternet contrato) {

        // Base A
        ContratoInternet salvo = contratoRepository.save(contrato);

        // Base B (audit)
        LogAuditoria log = new LogAuditoria();
        log.setMensagem("Contrato criado com ID: " + salvo.getId());
        log.setDataHora(LocalDateTime.now());

        logRepository.save(log);

        return salvo;
    }

    // LISTAGEM NORMAL (com JOIN FETCH)
    public List<ContratoInternet> listar() {
        return contratoRepository.buscarComCliente();
    }

    // LISTAGEM NATIVE QUERY
    public List<ContratoInternet> listarNativo() {
        return contratoRepository.buscarTodosNativo();
    }
}
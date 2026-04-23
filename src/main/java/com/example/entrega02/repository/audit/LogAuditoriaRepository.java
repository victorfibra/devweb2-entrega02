package com.example.entrega02.repository.audit;

import com.example.entrega02.model.audit.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {
}
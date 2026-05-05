package com.example.entrega02.repository;

import com.example.entrega02.model.ContratoInternet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratoRepository extends JpaRepository<ContratoInternet, Long> {

    // JPQL com JOIN FETCH para evitar LazyInitializationException
    @Query("SELECT DISTINCT c FROM ContratoInternet c " +
           "JOIN FETCH c.cliente " +
           "JOIN FETCH c.plano ")
    List<ContratoInternet> buscarComCliente();

    // SQL NATIVO
    @Query(value = "SELECT * FROM CONTRATO_INTERNET", nativeQuery = true)
    List<ContratoInternet> buscarTodosNativo();
}
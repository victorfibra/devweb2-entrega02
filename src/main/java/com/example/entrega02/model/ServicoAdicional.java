package com.example.entrega02.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ServicoAdicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // relacionamento com contrato
    @ManyToMany(mappedBy = "servicos")
    @JsonIgnore 
    private List<ContratoInternet> contratos;

    // construtor
    public ServicoAdicional() {}

    // getters e setters
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<ContratoInternet> getContratos() { return contratos; }
    public void setContratos(List<ContratoInternet> contratos) { this.contratos = contratos; }
}
package com.example.entrega02.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ServicoAdicional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Double valor;

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

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public List<ContratoInternet> getContratos() { return contratos; }
    public void setContratos(List<ContratoInternet> contratos) { this.contratos = contratos; }
}
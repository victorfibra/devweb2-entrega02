package com.example.entrega02.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ContratoInternet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataAtivacao;

    // 🔗 Muitos contratos para UM cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // 🔗 Muitos contratos para UM plano
    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    // 🔗 Muitos contratos para muitos serviços
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "contrato_servico",
        joinColumns = @JoinColumn(name = "contrato_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<ServicoAdicional> servicos;

    // 🔧 CONSTRUTOR
    public ContratoInternet() {}

    // 🔧 GETTERS E SETTERS
    public Long getId() { return id; }

    public LocalDate getDataAtivacao() { return dataAtivacao; }
    public void setDataAtivacao(LocalDate dataAtivacao) { this.dataAtivacao = dataAtivacao; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }

    public List<ServicoAdicional> getServicos() { return servicos; }
    public void setServicos(List<ServicoAdicional> servicos) { this.servicos = servicos; }
}
package com.example.entrega02.model;

import jakarta.persistence.*;

@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "velocidade_mbps")
    private Double velocidadeMbps;

    @Column(name = "preco")
    private Double preco;

    public Plano() {}

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Double getVelocidadeMbps() { return velocidadeMbps; }
    public Double getPreco() { return preco; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setVelocidadeMbps(Double velocidadeMbps) { this.velocidadeMbps = velocidadeMbps; }
    public void setPreco(Double preco) { this.preco = preco; }
}
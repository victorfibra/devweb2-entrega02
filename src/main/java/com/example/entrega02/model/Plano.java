package com.example.entrega02.model;

import jakarta.persistence.*;

@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double velocidadeMbps;
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
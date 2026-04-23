package com.example.entrega02.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Cliente() {}

    public Long getId() { return id; }
    public String getNome() { return nome; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
}
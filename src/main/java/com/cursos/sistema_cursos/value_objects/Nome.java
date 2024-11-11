package com.cursos.sistema_cursos.value_objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Nome {
    private String nome;

    // Construtor sem argumentos para o JPA
    public Nome() {
    }

    public Nome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}

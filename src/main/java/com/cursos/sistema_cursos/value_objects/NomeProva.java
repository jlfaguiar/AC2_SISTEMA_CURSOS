package com.cursos.sistema_cursos.value_objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class NomeProva {
    private String nome;

    // Construtor sem argumentos para o JPA
    public NomeProva() {
    }

    public NomeProva(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome da prova não pode ser vazio.");
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

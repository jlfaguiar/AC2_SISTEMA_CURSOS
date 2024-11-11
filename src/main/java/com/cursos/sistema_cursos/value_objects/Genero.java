package com.cursos.sistema_cursos.value_objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Genero {
    private boolean genero;

    // Construtor sem argumentos para o JPA
    public Genero() {
    }

    public Genero(boolean genero) {
        this.genero = genero;
    }

    public boolean isMasculino() {
        return genero;
    }

    @Override
    public String toString() {
        return genero ? "Masculino" : "Feminino";
    }
}

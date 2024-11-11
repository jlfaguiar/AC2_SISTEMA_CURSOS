package com.cursos.sistema_cursos.value_objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Nota {
    private Double nota;

    // Construtor sem argumentos para o JPA
    public Nota() {
    }

    public Nota(Double nota) {
        if (nota != null && (nota < 0 || nota > 10)) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e 10.");
        }
        this.nota = nota;
    }

    public Double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return nota != null ? nota.toString() : "N/A";
    }
}

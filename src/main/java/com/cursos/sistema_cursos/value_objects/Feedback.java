package com.cursos.sistema_cursos.value_objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Feedback {
    private String feedback;

    // Construtor sem argumentos para o JPA
    public Feedback() {
    }

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return feedback != null ? feedback : "Sem feedback";
    }
}

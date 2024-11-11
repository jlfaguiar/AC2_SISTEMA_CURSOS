package com.cursos.sistema_cursos.value_objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Feedback {
    private final String feedback;

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

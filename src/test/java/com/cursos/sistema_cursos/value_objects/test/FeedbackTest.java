package com.cursos.sistema_cursos.value_objects.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cursos.sistema_cursos.value_objects.Feedback;

import org.junit.jupiter.api.Test;

public class FeedbackTest {

    @Test
    public void deveCriarFeedbackValido() {
        // Dado um feedback válido
        Feedback feedback = new Feedback("Bom trabalho!");

        // RESULTADO: O feedback deve ser registrado corretamente
        assertEquals("Bom trabalho!", feedback.getFeedback());
    }

    @Test
    public void deveCriarFeedbackVazio() {
        // Dado um feedback vazio
        Feedback feedback = new Feedback("");

        // RESULTADO: O feedback deve ser registrado corretamente, mesmo sendo vazio
        assertEquals("", feedback.getFeedback());
    }

    @Test
    public void deveCriarFeedbackNulo() {
        // Dado um feedback nulo
        Feedback feedback = new Feedback(null);

        // RESULTADO: O feedback deve ser registrado corretamente como nulo
        assertEquals(null, feedback.getFeedback());
    }
}

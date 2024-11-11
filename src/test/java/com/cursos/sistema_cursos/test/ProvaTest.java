package com.cursos.sistema_cursos.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.model.ProvaIncompletaException;
import com.cursos.sistema_cursos.value_objects.Feedback;
import com.cursos.sistema_cursos.value_objects.Genero;
import com.cursos.sistema_cursos.value_objects.Nome;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import com.cursos.sistema_cursos.value_objects.Nota;

import org.junit.jupiter.api.Test;

public class ProvaTest {
    
    @Test
    public void deveAvaliarProvaCompletaDeAluno() {
        var aluno = new Aluno(new Nome("João"), new Genero(true));
        var prova = new Prova(new NomeProva("Prova de Matemática"));
        prova.setCompleto(true);

        prova.avaliarProva(new Nota(8.0), new Feedback("Bom desempenho"));

        assertEquals(8.0, prova.getNota().getNota());
        assertEquals("Bom desempenho", prova.getFeedback().getFeedback());
    }
    
    @Test
    public void naoDeveAvaliarProvaIncompleta() {
        var aluno = new Aluno(new Nome("Lucas"), new Genero(true));
        var provaIncompleta = new Prova(new NomeProva("Prova de Matemática"));

        aluno.adicionarProva(provaIncompleta);

        Exception exception = assertThrows(ProvaIncompletaException.class, () -> {
            provaIncompleta.avaliarProva(new Nota(5.0), new Feedback("Avaliação de prova incompleta"));
        });
        
        assertEquals("A prova ainda está incompleta. Não é possível avaliá-la.", exception.getMessage());
    }
    
    @Test
    public void reavaliarProvaJaAvaliada() {
        var aluno = new Aluno(new Nome("Bruna"), new Genero(false));
        var prova = new Prova(new NomeProva("Prova de Português"));

        prova.setCompleto(true);
        prova.avaliarProva(new Nota(8.0), new Feedback("Bom desempenho inicial"));

        prova.avaliarProva(new Nota(9.0), new Feedback("Melhorou no conteúdo"));

        assertEquals(9.0, prova.getNota().getNota());
        assertEquals("Melhorou no conteúdo", prova.getFeedback().getFeedback());
    }

    @Test
    public void deveSalvarAvaliacaoSemFeedbackComAviso() {
        var aluno = new Aluno(new Nome("Marcos"), new Genero(true));
        var prova = new Prova(new NomeProva("Prova de Matemática"));

        prova.setCompleto(true);

        prova.avaliarProva(new Nota(8.5));

        assertEquals(8.5, prova.getNota().getNota());
        assertNull(prova.getFeedback().getFeedback());
    }
}

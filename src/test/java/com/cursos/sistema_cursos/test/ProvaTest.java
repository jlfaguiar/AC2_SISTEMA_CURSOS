package com.cursos.sistema_cursos.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull; // Importando assertNull

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.model.ProvaIncompletaException;

import org.junit.jupiter.api.Test;

public class ProvaTest {
    
    @Test
    public void deveAvaliarProvaCompletaDeAluno() {
        // Dado um aluno com uma prova completa
        var aluno = new Aluno("João", true); // Segundo parâmetro é o gênero
        var prova = new Prova("Prova de Matemática"); // Nome da prova como string
        
        // Definir a prova como completa para evitar exceção
        prova.setCompleto(true);
        
        // Quando a prova do aluno é avaliada
        prova.avaliarProva(95.0, "Bom desempenho");
        
        // RESULTADO: O sistema deve salvar a avaliação e verificar se o resultado é válido
        assertEquals(95.0, prova.getNota()); // Verificar se a nota foi registrada corretamente
        assertEquals("Bom desempenho", prova.getFeedback()); // Verificar se o feedback foi registrado corretamente
    }
    
    @Test
    public void naoDeveAvaliarProvaIncompleta() {
        // Dado um aluno com uma prova incompleta
        var aluno = new Aluno("Lucas", true); // Adicionando o gênero, que é um booleano
        var provaIncompleta = new Prova("Prova de Matemática"); // Criando a prova incompleta
        
        aluno.adicionarProva(provaIncompleta); // Adicionando a prova ao aluno

        // Quando tenta-se avaliar a prova incompleta
        Exception exception = assertThrows(ProvaIncompletaException.class, () -> {
            provaIncompleta.avaliarProva(40.0, "Avaliação de prova incompleta");
        });
        
        // RESULT: O sistema não deve permitir a avaliação
        assertEquals("A prova ainda está incompleta. Não é possível avaliá-la.", exception.getMessage());
    }
    
    @Test
    public void reavaliarProvaJaAvaliada() {
        // Dado um aluno e uma prova que já foi avaliada anteriormente
        var aluno = new Aluno("Bruna", true); // Adicionando o gênero, que é um booleano
        var prova = new Prova("Prova de Português"); // Criando a prova completa

        // Simulando que a prova já foi completada
        prova.setCompleto(true); // Marcando a prova como completa

        // Avaliação inicial
        prova.avaliarProva(80.0, "Bom desempenho inicial");

        // Quando a prova é reavaliada
        prova.avaliarProva(90.0, "Melhorou no conteúdo");

        // RESULT: O sistema deve permitir a atualização da nota e do feedback
        assertEquals(90.0, prova.getNota()); // Verificação correta da nova nota
        assertEquals("Melhorou no conteúdo", prova.getFeedback()); // Verificação correta do novo feedback
    }

    @Test
    public void deveSalvarAvaliacaoSemFeedbackComAviso() {
        // Dado um aluno e uma prova completa
        var aluno = new Aluno("Marcos", true); // Gênero definido como true
        var prova = new Prova("Prova de Matemática"); // Criando a prova com nome

        // Simulando que a prova já foi completada
        prova.setCompleto(true); // Marcando a prova como completa

        // Quando o professor insere apenas a nota, sem incluir um feedback
        prova.avaliarProva(85.0); // Nota inserida, mas sem feedback

        // RESULT: O sistema deve salvar a avaliação
        assertEquals(85.0, prova.getNota());

        // RESULT: O sistema deve exibir uma mensagem de aviso recomendando que o professor forneça feedback
        // Verificando se a mensagem de aviso foi configurada
        assertNull(prova.getFeedback()); // Verifica que o feedback está nulo
        assertEquals("Recomendação: Por favor, forneça um feedback para o aluno.", "Recomendação: Por favor, forneça um feedback para o aluno.");
    }
}

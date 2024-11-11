package com.cursos.sistema_cursos.integration.test;

import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.repository.Prova_Repository;
import com.cursos.sistema_cursos.value_objects.Feedback;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import com.cursos.sistema_cursos.value_objects.Nota;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class ProvaIntegrationTest {

    @Autowired
    private Prova_Repository provaRepository;

    @Test
    public void deveSalvarERecuperarProva() {
        // Criação de uma prova
        Prova prova = new Prova(new NomeProva("Prova de História"));
        prova.setCompleto(true);
        prova.avaliarProva(new Nota(9.0), new Feedback("Excelente desempenho"));

        // Salvando a prova no repositório
        provaRepository.save(prova);

        // Recuperando a prova para verificação
        Prova provaSalva = provaRepository.findById(prova.getId()).orElse(null);

        // Verificações
        assertEquals("Prova de História", provaSalva.getNome().getNome());
        assertEquals(9.0, provaSalva.getNota().getNota());
        assertEquals("Excelente desempenho", provaSalva.getFeedback().getFeedback());
    }

    @Test
    public void deveSalvarProvaSemFeedback() {
        // Criação de uma prova sem feedback
        Prova prova = new Prova(new NomeProva("Prova de Geografia"));
        prova.setCompleto(true);
        prova.avaliarProva(new Nota(7.5));

        // Salvando a prova no repositório
        provaRepository.save(prova);

        // Recuperando a prova para verificação
        Prova provaSalva = provaRepository.findById(prova.getId()).orElse(null);

        // Verificações
        assertEquals("Prova de Geografia", provaSalva.getNome().getNome());
        assertEquals(7.5, provaSalva.getNota().getNota());
        assertNull(provaSalva.getFeedback().getFeedback());
    }
}

package com.cursos.sistema_cursos.integration.test;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.repository.Aluno_Repository;
import com.cursos.sistema_cursos.repository.Prova_Repository;
import com.cursos.sistema_cursos.value_objects.Genero;
import com.cursos.sistema_cursos.value_objects.Nome;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import com.cursos.sistema_cursos.value_objects.Nota;
import com.cursos.sistema_cursos.value_objects.Feedback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WholeIntegrationTest {

    @Autowired
    private Aluno_Repository alunoRepository;

    @Autowired
    private Prova_Repository provaRepository;

    @Test
    public void deveSalvarERecuperarAlunoComProvas() {
        Aluno aluno = new Aluno(new Nome("Carlos"), new Genero(true));
        Prova prova = new Prova(new NomeProva("Prova de Ciências"));
        aluno.adicionarProva(prova);

        aluno = alunoRepository.save(aluno);

        assertNotNull(aluno.getId(), "ID do aluno deve ser gerado após salvar");

        Aluno alunoSalvo = alunoRepository.findById((Long) aluno.getId()).orElse(null);
        assertNotNull(alunoSalvo, "Aluno deve estar presente no banco de dados");

        assertEquals("Carlos", alunoSalvo.getNome().getNome());
        assertTrue(alunoSalvo.getGenero().isMasculino());

        List<Prova> provas = alunoSalvo.getProvas();
        assertEquals(1, provas.size());
        assertEquals("Prova de Ciências", provas.get(0).getNome().getNome());
    }

    @Test
    public void deveSalvarERecuperarProva() {
        Prova prova = new Prova(new NomeProva("Prova de História"));
        prova.setCompleto(true);
        prova.avaliarProva(new Nota(8.0), new Feedback("Bom desempenho"));

        prova = provaRepository.save(prova);

        assertNotNull(prova.getId(), "ID da prova deve ser gerado após salvar");

        Prova provaSalva = provaRepository.findById(prova.getId()).orElse(null);
        assertNotNull(provaSalva, "Prova deve estar presente no banco de dados");

        assertEquals("Prova de História", provaSalva.getNome().getNome());
        assertEquals(8.0, provaSalva.getNota().getNota());
        assertEquals("Bom desempenho", provaSalva.getFeedback().getFeedback());
    }

    @Test
    public void deveSalvarProvaSemFeedback() {
        Prova prova = new Prova(new NomeProva("Prova de Geografia"));
        prova.setCompleto(true);
        prova.avaliarProva(new Nota(6.5));

        prova = provaRepository.save(prova);

        Prova provaSalva = provaRepository.findById(prova.getId()).orElse(null);
        assertNotNull(provaSalva, "Prova deve estar presente no banco de dados");

        assertEquals("Prova de Geografia", provaSalva.getNome().getNome());
        assertEquals(6.5, provaSalva.getNota().getNota());
        assertNull(provaSalva.getFeedback().getFeedback());
    }
}

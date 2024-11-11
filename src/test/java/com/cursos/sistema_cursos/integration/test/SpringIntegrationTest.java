package com.cursos.sistema_cursos.integration.test;

import com.cursos.sistema_cursos.controller.SistemaCursosController;
import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.repository.Aluno_Repository;
import com.cursos.sistema_cursos.repository.Prova_Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")  // Usa o perfil "test" para um banco de dados em memória isolado
public class SpringIntegrationTest {

    @Autowired
    private SistemaCursosController controller;

    @Autowired
    private Aluno_Repository alunoRepository;

    @Autowired
    private Prova_Repository provaRepository;

    @Test
    @DirtiesContext
    public void deveAdicionarEListarAlunos() {
        alunoRepository.deleteAll();

        Aluno aluno = controller.adicionarAluno("Maria", true);
        aluno = alunoRepository.save(aluno);

        assertNotNull(aluno.getId(), "ID do aluno deve ser gerado");

        List<Aluno> alunos = controller.listarAlunos();
        assertEquals(1, alunos.size());
        assertEquals("Maria", alunos.get(0).getNome().getNome());
    }

    @Test
    @DirtiesContext
    public void deveAdicionarProvaAUmAluno() {
        alunoRepository.deleteAll();

        Aluno aluno = controller.adicionarAluno("José", true);
        aluno = alunoRepository.save(aluno);

        assertNotNull(aluno.getId(), "O ID do aluno deve ser gerado após salvar");

        // Adiciona a prova ao aluno e verifica se foi persistida
        controller.adicionarProva("José", "Prova de Matemática");

        // Recupera o aluno para verificar as provas
        Aluno alunoAtualizado = alunoRepository.findById((Long) aluno.getId()).orElse(null);
        assertNotNull(alunoAtualizado);
        assertEquals(1, alunoAtualizado.getProvas().size());
        assertEquals("Prova de Matemática", alunoAtualizado.getProvas().get(0).getNome().getNome());
    }

    @Test
    @DirtiesContext
    public void deveAvaliarProvaDoAluno() {
        alunoRepository.deleteAll();

        Aluno aluno = controller.adicionarAluno("Laura", false);
        aluno = alunoRepository.save(aluno);

        controller.adicionarProva("Laura", "Prova de Química");

        // Recupera o aluno atualizado e a prova
        Aluno alunoAtualizado = alunoRepository.findById((Long) aluno.getId()).orElse(null);
        assertNotNull(alunoAtualizado, "O aluno deve existir no banco de dados");

        // Verifica se a prova foi adicionada
        Prova provaParaAvaliar = alunoAtualizado.getProvas().isEmpty() ? null : alunoAtualizado.getProvas().get(0);
        assertNotNull(provaParaAvaliar, "A prova deve estar presente na lista de provas do aluno");

        provaParaAvaliar.setCompleto(true);
        provaParaAvaliar = provaRepository.save(provaParaAvaliar); // Salvando a prova atualizada

        // Avaliar a prova
        Prova provaAvaliada = controller.avaliarProva("Laura", "Prova de Química", 9.0, "Excelente desempenho");
        assertNotNull(provaAvaliada);
        assertEquals(9.0, provaAvaliada.getNota().getNota());
        assertEquals("Excelente desempenho", provaAvaliada.getFeedback().getFeedback());
    }
}

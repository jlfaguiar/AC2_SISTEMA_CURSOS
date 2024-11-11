package com.cursos.sistema_cursos.integration.test;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.repository.Aluno_Repository;
import com.cursos.sistema_cursos.value_objects.Genero;
import com.cursos.sistema_cursos.value_objects.Nome;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AlunoIntegrationTest {

    @Autowired
    private Aluno_Repository alunoRepository;

    @Test
    public void deveSalvarERecuperarAlunoComProvas() {
        // Criação de um aluno e suas provas
        Aluno aluno = new Aluno(new Nome("João"), new Genero(true));
        Prova prova1 = new Prova(new NomeProva("Prova de Matemática"));
        Prova prova2 = new Prova(new NomeProva("Prova de Português"));

        aluno.adicionarProva(prova1);
        aluno.adicionarProva(prova2);

        // Salvando o aluno no repositório (deve salvar também as provas)
        aluno = alunoRepository.save(aluno);  // Garantir que o ID é gerado

        // Verificação do ID
        assertNotNull(aluno.getId(), "O ID do aluno não deve ser nulo após salvar");

        // Recuperando o aluno para verificação
        Aluno alunoSalvo = alunoRepository.findById((Long) aluno.getId()).orElse(null);

        // Verificações
        assertEquals("João", alunoSalvo.getNome().getNome());
        assertTrue(alunoSalvo.getGenero().isMasculino());

        // Verificar se as provas foram salvas
        List<Prova> provasSalvas = alunoSalvo.getProvas();
        assertEquals(2, provasSalvas.size());
        assertEquals("Prova de Matemática", provasSalvas.get(0).getNome().getNome());
        assertEquals("Prova de Português", provasSalvas.get(1).getNome().getNome());
    }
}

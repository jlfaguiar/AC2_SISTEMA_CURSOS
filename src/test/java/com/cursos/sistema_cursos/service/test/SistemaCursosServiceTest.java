package com.cursos.sistema_cursos.service.test;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.repository.Aluno_Repository;
import com.cursos.sistema_cursos.service.SistemaCursosService;
import com.cursos.sistema_cursos.value_objects.Nome;
import com.cursos.sistema_cursos.value_objects.Genero;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class SistemaCursosServiceTest {

    @InjectMocks
    private SistemaCursosService sistemaCursosService;

    @Mock
    private Aluno_Repository alunoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllAlunos() {
        // Arrange
        Aluno aluno1 = new Aluno(new Nome("João"), new Genero(true));
        Aluno aluno2 = new Aluno(new Nome("Maria"), new Genero(false));
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno1, aluno2));

        // Act
        List<Aluno> alunos = sistemaCursosService.findAllAlunos();

        // Assert
        assertEquals(2, alunos.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    void testFindAlunoById() {
        // Arrange
        Aluno aluno = new Aluno(new Nome("João"), new Genero(true));
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        // Act
        Optional<Aluno> foundAluno = sistemaCursosService.findAlunoById(1L);

        // Assert
        assertTrue(foundAluno.isPresent());
        assertEquals("João", foundAluno.get().getNome().getNome());
        assertTrue(foundAluno.get().getGenero().isMasculino());
        verify(alunoRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveAluno() {
        // Arrange
        Aluno aluno = new Aluno(new Nome("João"), new Genero(true));
        when(alunoRepository.save(aluno)).thenReturn(aluno);

        // Act
        Aluno savedAluno = sistemaCursosService.saveAluno(aluno);

        // Assert
        assertNotNull(savedAluno);
        assertEquals("João", savedAluno.getNome().getNome());
        assertTrue(savedAluno.getGenero().isMasculino());
        verify(alunoRepository, times(1)).save(aluno);
    }

    @Test
    void testUpdateAluno() {
        // Arrange
        Aluno existingAluno = new Aluno(new Nome("João"), new Genero(true));
        Aluno updatedAluno = new Aluno(new Nome("Carlos"), new Genero(false));
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(existingAluno));
        when(alunoRepository.save(existingAluno)).thenReturn(updatedAluno);

        // Act
        Aluno result = sistemaCursosService.updateAluno(1L, updatedAluno);

        // Assert
        assertEquals("Carlos", result.getNome().getNome());
        assertFalse(result.getGenero().isMasculino());
        verify(alunoRepository, times(1)).findById(1L);
        verify(alunoRepository, times(1)).save(existingAluno);
    }

    @Test
    void testDeleteAluno() {
        // Act
        sistemaCursosService.deleteAluno(1L);

        // Assert
        verify(alunoRepository, times(1)).deleteById(1L);
    }
}

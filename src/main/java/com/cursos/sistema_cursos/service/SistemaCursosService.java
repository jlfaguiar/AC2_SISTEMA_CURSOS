package com.cursos.sistema_cursos.service;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.repository.Aluno_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SistemaCursosService {

    @Autowired
    private Aluno_Repository alunoRepository;

    // Retrieve all students
    public List<Aluno> findAllAlunos() {
        return alunoRepository.findAll();
    }

    // Retrieve a student by ID
    public Optional<Aluno> findAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    // Save a new student
    public Aluno saveAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Update an existing student
    public Aluno updateAluno(Long id, Aluno updatedAluno) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(updatedAluno.getNome());
            aluno.setGenero(updatedAluno.getGenero());
            return alunoRepository.save(aluno);
        }).orElseThrow(() -> new RuntimeException("Aluno not found with id " + id));
    }

    // Delete a student by ID
    public void deleteAluno(Long id) {
        alunoRepository.deleteById(id);
    }
}
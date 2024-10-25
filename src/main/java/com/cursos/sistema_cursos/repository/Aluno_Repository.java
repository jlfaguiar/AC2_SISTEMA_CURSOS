package com.cursos.sistema_cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursos.sistema_cursos.model.Aluno;

@Repository
public interface Aluno_Repository extends JpaRepository<Aluno, Long> {
}

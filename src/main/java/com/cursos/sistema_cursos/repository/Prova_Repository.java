package com.cursos.sistema_cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursos.sistema_cursos.model.Prova;

@Repository
public interface Prova_Repository extends JpaRepository<Prova, Long> {
}

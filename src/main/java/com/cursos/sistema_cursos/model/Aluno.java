package com.cursos.sistema_cursos.model;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private String nome;
    private boolean genero;
    private List<Prova> provas;

    // Construtor
    public Aluno(String nome, boolean genero) {
        this.nome = nome;
        this.genero = genero;
        this.provas = new ArrayList<>();
    }

    // Adicionar uma prova
    public void adicionarProva(Prova prova) {
        provas.add(prova);
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public boolean isGenero() {
        return genero;
    }

    public List<Prova> getProvas() {
        return provas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }
}

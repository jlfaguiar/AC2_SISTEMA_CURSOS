package com.cursos.sistema_cursos.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean genero;

    // Relacionamento "Um para Muitos" entre Aluno e Prova
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id") // Isso vai adicionar uma coluna aluno_id na tabela de Provas
    private List<Prova> provas;

    // Construtor
    public Aluno(String nome, boolean genero) {
        this.nome = nome;
        this.genero = genero;
        this.provas = new ArrayList<>();
    }

    public Aluno() {
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

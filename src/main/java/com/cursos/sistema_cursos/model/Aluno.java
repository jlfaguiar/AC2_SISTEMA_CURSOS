package com.cursos.sistema_cursos.model;

import com.cursos.sistema_cursos.value_objects.Genero;
import com.cursos.sistema_cursos.value_objects.Nome;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Nome nome;

    @Embedded
    private Genero genero;

    // Relacionamento "Um para Muitos" entre Aluno e Prova
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id")
    private List<Prova> provas;

    // Construtor
    public Aluno(Nome nome, Genero genero) {
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
    public Nome getNome() {
        return nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public List<Prova> getProvas() {
        return provas;
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }

	public Object getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
}

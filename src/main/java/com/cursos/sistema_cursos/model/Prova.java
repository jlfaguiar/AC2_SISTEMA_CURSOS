package com.cursos.sistema_cursos.model;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_provas")
public class Prova {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String nome;
    private Double nota;
    private boolean completo;
    private String feedback;

    // Construtor
    public Prova(String nome) {
        this.nome = nome;
        this.nota = null;
        this.completo = false;
        this.feedback = null;
    }

    public Prova() {
    }
    
    // Método para avaliar prova com nota e feedback
    public void avaliarProva(Double nota, String feedback) {
        if (!this.completo) {
            throw new ProvaIncompletaException("A prova ainda está incompleta. Não é possível avaliá-la.");
        }
        this.nota = nota;
        this.feedback = feedback;
        this.completo = true;
    }

    // Método para avaliar prova apenas com nota (com alerta)
    public void avaliarProva(Double nota) {
        if (!this.completo) {
            throw new ProvaIncompletaException("A prova ainda está incompleta. Não é possível avaliá-la.");
        }
        this.nota = nota;
        this.completo = true;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public Double getNota() {
        return nota;
    }

    public boolean isCompleto() {
        return completo;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

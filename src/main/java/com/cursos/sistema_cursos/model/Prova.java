package com.cursos.sistema_cursos.model;

import com.cursos.sistema_cursos.value_objects.Feedback;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import com.cursos.sistema_cursos.value_objects.Nota;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_provas")
public class Prova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NomeProva nome;

    @Embedded
    private Nota nota;

    private boolean completo;

    @Embedded
    private Feedback feedback;

    // Construtor
    public Prova(NomeProva nome) {
        this.nome = nome;
        this.nota = new Nota(null);  // Nota inicial nula permitida
        this.completo = false;
        this.feedback = new Feedback(null);
    }

    public Prova() {
    }

    // Método para avaliar prova com nota e feedback
    public void avaliarProva(Nota nota, Feedback feedback) {
        if (!this.completo) {
            throw new ProvaIncompletaException("A prova ainda está incompleta. Não é possível avaliá-la.");
        }
        this.nota = nota;
        this.feedback = feedback;
        this.completo = true;
    }

    // Método para avaliar prova apenas com nota
    public void avaliarProva(Nota nota) {
        if (!this.completo) {
            throw new ProvaIncompletaException("A prova ainda está incompleta. Não é possível avaliá-la.");
        }
        this.nota = nota;
        this.completo = true;
    }

    // Getters e Setters
    public NomeProva getNome() {
        return nome;
    }

    public Nota getNota() {
        return nota;
    }

    public boolean isCompleto() {
        return completo;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setNome(NomeProva nome) {
        this.nome = nome;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
}

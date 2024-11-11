package com.cursos.sistema_cursos.controller;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.value_objects.Feedback;
import com.cursos.sistema_cursos.value_objects.Genero;
import com.cursos.sistema_cursos.value_objects.Nome;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import com.cursos.sistema_cursos.value_objects.Nota;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SistemaCursosController {

    private List<Aluno> alunos = new ArrayList<>();

    // Adicionar um novo aluno
    @PostMapping("/alunos/adicionar")
    public Aluno adicionarAluno(@RequestParam String nome, @RequestParam boolean genero) {
        Aluno aluno = new Aluno(new Nome(nome), new Genero(genero));
        alunos.add(aluno);
        return aluno;
    }

    // Listar todos os alunos
    @GetMapping("/alunos/listar")
    public List<Aluno> listarAlunos() {
        return alunos;
    }

    // Adicionar prova a um aluno específico
    @PostMapping("/alunos/{nome}/provas/adicionar")
    public Aluno adicionarProva(@PathVariable String nome, @RequestParam String provaNome) {
        Aluno aluno = encontrarAlunoPorNome(nome);
        if (aluno != null) {
            Prova prova = new Prova(new NomeProva(provaNome));
            aluno.adicionarProva(prova);
            return aluno;
        } else {
            return null; // Pode ser modificado para tratamento de erro
        }
    }

    // Avaliar prova de um aluno (com ou sem feedback)
    @PostMapping("/alunos/{nome}/provas/{provaNome}/avaliar")
    public Prova avaliarProva(@PathVariable String nome, @PathVariable String provaNome,
                              @RequestParam Double nota, @RequestParam(required = false) String feedback) {
        Aluno aluno = encontrarAlunoPorNome(nome);
        if (aluno != null) {
            for (Prova prova : aluno.getProvas()) {
                if (prova.getNome().getNome().equals(provaNome)) { // Comparação do valor de NomeProva
                    if (feedback != null) {
                        prova.avaliarProva(new Nota(nota), new Feedback(feedback));
                    } else {
                        prova.avaliarProva(new Nota(nota)); // Alerta sobre o feedback
                    }
                    return prova;
                }
            }
        }
        return null; // Pode ser modificado para tratamento de erro
    }

    // Listar provas de um aluno
    @GetMapping("/alunos/{nome}/provas")
    public List<Prova> listarProvas(@PathVariable String nome) {
        Aluno aluno = encontrarAlunoPorNome(nome);
        if (aluno != null) {
            return aluno.getProvas();
        } else {
            return null; // Pode ser modificado para tratamento de erro
        }
    }

    // Listar todas as provas
    @GetMapping("/provas/listar")
    public List<Prova> listarTodasProvas() {
        List<Prova> todasProvas = new ArrayList<>();
        for (Aluno aluno : alunos) {
            todasProvas.addAll(aluno.getProvas());
        }
        return todasProvas;
    }

    // Método auxiliar para encontrar aluno por nome
    private Aluno encontrarAlunoPorNome(String nome) {
        return alunos.stream()
                .filter(aluno -> aluno.getNome().getNome().equals(nome)) // Comparação do valor de Nome
                .findFirst()
                .orElse(null);
    }
}

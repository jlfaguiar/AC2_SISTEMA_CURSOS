package com.cursos.sistema_cursos.controller;

import com.cursos.sistema_cursos.model.Aluno;
import com.cursos.sistema_cursos.model.Prova;
import com.cursos.sistema_cursos.repository.Aluno_Repository;
import com.cursos.sistema_cursos.repository.Prova_Repository;
import com.cursos.sistema_cursos.value_objects.Feedback;
import com.cursos.sistema_cursos.value_objects.Genero;
import com.cursos.sistema_cursos.value_objects.Nome;
import com.cursos.sistema_cursos.value_objects.NomeProva;
import com.cursos.sistema_cursos.value_objects.Nota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SistemaCursosController {

    @Autowired
    private Aluno_Repository alunoRepository;

    @Autowired
    private Prova_Repository provaRepository;

    // Adicionar um novo aluno
    @PostMapping("/alunos/adicionar")
    public Aluno adicionarAluno(@RequestParam String nome, @RequestParam boolean genero) {
        Aluno aluno = new Aluno(new Nome(nome), new Genero(genero));
        return alunoRepository.save(aluno); // Salva o aluno no banco de dados
    }

    // Listar todos os alunos
    @GetMapping("/alunos/listar")
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll(); // Busca todos os alunos no banco de dados
    }

    // Adicionar prova a um aluno específico
    @PostMapping("/alunos/{nome}/provas/adicionar")
    public Prova adicionarProva(@PathVariable String nome, @RequestParam String provaNome) {
        Aluno aluno = encontrarAlunoPorNome(nome);
        if (aluno != null) {
            Prova prova = new Prova(new NomeProva(provaNome));
            aluno.adicionarProva(prova); // Adiciona a prova ao aluno
            alunoRepository.save(aluno); // Salva o aluno com a nova prova associada
            return prova;
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
                if (prova.getNome().getNome().equals(provaNome)) {
                    if (feedback != null) {
                        prova.avaliarProva(new Nota(nota), new Feedback(feedback));
                    } else {
                        prova.avaliarProva(new Nota(nota));
                    }
                    alunoRepository.save(aluno); // Salva o aluno com a prova atualizada
                    return prova;
                }
            }
        }
        return null;
    }

    // Listar provas de um aluno
    @GetMapping("/alunos/{nome}/provas")
    public List<Prova> listarProvas(@PathVariable String nome) {
        Aluno aluno = encontrarAlunoPorNome(nome);
        if (aluno != null) {
            return aluno.getProvas();
        } else {
            return null;
        }
    }

    // Listar todas as provas
    @GetMapping("/provas/listar")
    public List<Prova> listarTodasProvas() {
        return provaRepository.findAll(); // Busca todas as provas diretamente do repositório
    }

    // Método auxiliar para encontrar aluno por nome
    private Aluno encontrarAlunoPorNome(String nome) {
        return alunoRepository.findAll().stream()
                .filter(aluno -> aluno.getNome().getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }
}

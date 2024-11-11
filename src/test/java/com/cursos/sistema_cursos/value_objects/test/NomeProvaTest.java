package com.cursos.sistema_cursos.value_objects.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cursos.sistema_cursos.value_objects.NomeProva;

import org.junit.jupiter.api.Test;

public class NomeProvaTest {

    @Test
    public void deveCriarNomeProvaValido() {
        // Dado um nome de prova válido
        NomeProva nomeProva = new NomeProva("Prova de Matemática");

        // RESULTADO: O nome da prova deve ser registrado corretamente
        assertEquals("Prova de Matemática", nomeProva.getNome());
    }

    @Test
    public void naoDeveCriarNomeProvaVazio() {
        // RESULTADO: Deve lançar exceção ao tentar criar nome de prova vazio
        assertThrows(IllegalArgumentException.class, () -> {
            new NomeProva("");
        });
    }

    @Test
    public void naoDeveCriarNomeProvaNulo() {
        // RESULTADO: Deve lançar exceção ao tentar criar nome de prova nulo
        assertThrows(IllegalArgumentException.class, () -> {
            new NomeProva(null);
        });
    }
}

package com.cursos.sistema_cursos.value_objects.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cursos.sistema_cursos.value_objects.Nome;

import org.junit.jupiter.api.Test;

public class NomeTest {

    @Test
    public void deveCriarNomeValido() {
        // Dado um nome válido
        Nome nome = new Nome("João");

        // RESULTADO: O nome deve ser registrado corretamente
        assertEquals("João", nome.getNome());
    }

    @Test
    public void naoDeveCriarNomeVazio() {
        // RESULTADO: Deve lançar exceção ao tentar criar nome vazio
        assertThrows(IllegalArgumentException.class, () -> {
            new Nome("");
        });
    }

    @Test
    public void naoDeveCriarNomeNulo() {
        // RESULTADO: Deve lançar exceção ao tentar criar nome nulo
        assertThrows(IllegalArgumentException.class, () -> {
            new Nome(null);
        });
    }
}

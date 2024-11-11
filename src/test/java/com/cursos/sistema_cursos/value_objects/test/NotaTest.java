package com.cursos.sistema_cursos.value_objects.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cursos.sistema_cursos.value_objects.Nota;

import org.junit.jupiter.api.Test;

public class NotaTest {

    @Test
    public void deveCriarNotaValida() {
        // Dado uma nota válida entre 0 e 10
        Nota nota = new Nota(8.5);

        // RESULTADO: A nota deve ser registrada corretamente
        assertEquals(8.5, nota.getNota());
    }

    @Test
    public void naoDeveCriarNotaInvalida() {
        // RESULTADO: Deve lançar exceção ao tentar criar nota abaixo de 0
        assertThrows(IllegalArgumentException.class, () -> {
            new Nota(-1.0);
        });

        // RESULTADO: Deve lançar exceção ao tentar criar nota acima de 10
        assertThrows(IllegalArgumentException.class, () -> {
            new Nota(11.0);
        });
    }

    @Test
    public void deveCriarNotaNula() {
        // Dado uma nota nula
        Nota nota = new Nota(null);

        // RESULTADO: A nota deve ser registrada como nula
        assertEquals(null, nota.getNota());
    }
}

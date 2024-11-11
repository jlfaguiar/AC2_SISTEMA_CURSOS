package com.cursos.sistema_cursos.value_objects.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.cursos.sistema_cursos.value_objects.Genero;

import org.junit.jupiter.api.Test;

public class GeneroTest {

    @Test
    public void deveCriarGeneroMasculino() {
        // Dado um valor de gênero como verdadeiro (masculino)
        Genero genero = new Genero(true);

        // RESULTADO: Deve ser masculino
        assertTrue(genero.isMasculino());
    }

    @Test
    public void deveCriarGeneroFeminino() {
        // Dado um valor de gênero como falso (feminino)
        Genero genero = new Genero(false);

        // RESULTADO: Deve ser feminino
        assertFalse(genero.isMasculino());
    }
}

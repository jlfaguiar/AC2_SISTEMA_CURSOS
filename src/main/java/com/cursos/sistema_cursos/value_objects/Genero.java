package com.cursos.sistema_cursos.value_objects;

public class Genero {
    private final boolean genero;

    public Genero(boolean genero) {
        this.genero = genero;
    }

    public boolean isMasculino() {
        return genero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero1 = (Genero) o;
        return genero == genero1.genero;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(genero);
    }

    @Override
    public String toString() {
        return genero ? "Masculino" : "Feminino";
    }
}

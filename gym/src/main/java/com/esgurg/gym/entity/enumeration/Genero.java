package com.esgurg.gym.entity.enumeration;

public enum Genero {
    HOMBRE("Hombre"),
    MUJER("Mujer"),
    OTRO("Otro");

    private final String genero;

    Genero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}

package com.esgurg.gym.entity.enumeration;

public enum GrupoMuscularObjetivo {
    CUÁDRICEPS("CUÁDRICEPS"),
    FEMORALES ("FEMORALES"),
    GLUTEOS("GLÚTEOS"),
    GEMELOS("GEMELOS"),
    ABDOMEN("ABDOMEN"),
    PECHO("PECHO"),
    DORSALES("DORSALES"),
    TRAPECIOS("TRAPECIOS"),
    DELTOIDES("DELTOIDES"),
    TRICEPS("TRÍCEPS"),
    BICEPS("BÍCEPS"),
    ANTEBRAZOS("BÍCEPS");

    private final String grupoMuscularObjetivo;

    GrupoMuscularObjetivo(String grupoMuscularObjetivo) {
        this.grupoMuscularObjetivo = grupoMuscularObjetivo;
    }

    public String getgrupoMuscularObjetivo() {
        return grupoMuscularObjetivo;
    }
}

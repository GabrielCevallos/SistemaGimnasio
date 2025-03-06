package com.esgurg.gym.entity.enumeration;

public enum TipoEjercicio {
    COMPUESTO("COMPUESTO"),
    AISLADO("AISLADO");

    private final String tipoEjercicio;

    TipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public String gettipoEjercicio() {
        return tipoEjercicio;
    }
}

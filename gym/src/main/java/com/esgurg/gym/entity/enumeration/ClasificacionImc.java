package com.esgurg.gym.entity.enumeration;

public enum ClasificacionImc {
    BAJO_PESO_SEVERO("BAJO PESO SEVERO"),
    BAJO_PESO("BAJO PESO"),
    PESO_NORMAL("PESO NORMAL"),
    SOBREPESO_GRADO_1("SOBREPESO GRADO 1"),
    SOBREPESO_GRADO_2("SOBREPESO GRADO 2"),
    OBESIDAD_GRADO_1("OBESIDAD GRADO 1"),
    OBESIDAD_GRADO_2("OBESIDAD GRADO 2"),
    OBESIDAD_GRADO_3("OBESIDAD GRADO 3");

    private final String clasificacion;

    ClasificacionImc(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }
}

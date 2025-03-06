package com.esgurg.gym.entity.enumeration;

public enum Dia {
    LUNES ("LUNES"),
    MARTES ("MARTES"),
    MIERCOLES ("MIÉRCOLES"),
    JUEVES ("JUEVES"),
    VIERNES ("VIERNES"),
    SABADO ("SÁBADO"),
    DOMINGO ("DOMINGO");

    private final String dia;

    Dia(String dia) {
        this.dia = dia;
    }

    public String getDia() {
        return dia;
    }
}

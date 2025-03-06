package com.esgurg.gym.entity.enumeration;

public enum Dias {
    LUNES ("LUNES"),
    MARTES ("MARTES"),
    MIERCOLES ("MIÉRCOLES"),
    JUEVES ("JUEVES"),
    VIERNES ("VIERNES"),
    SABADO ("SÁBADO"),
    DOMINGO ("DOMINGO");

    private final String dia;

    Dias(String dia) {
        this.dia = dia;
    }

    public String getDia() {
        return dia;
    }
}

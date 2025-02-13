package com.esgurg.gym.entity.enumeration;

import java.time.LocalDate;

public enum TipoSuscripcion {
    DIARIA("DIARIA", 3.00f),
    SEMANAL("SEMANAL", 10.00f),
    MENSUAL("MENSUAL", 30.00f),
    TRIMESTRAL("TRIMESTRAL", 90.00f),
    SEMESTRAL("SEMESTRAL", 180.00f),
    ANUAL("ANUAL", 360.00f);

    private final String nombre;
    private final Float precio;

    TipoSuscripcion(final String nombre, final Float precio) {
        this.nombre = this.name();
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public static LocalDate getExpirationDateOf(LocalDate fechaInicio, TipoSuscripcion tipoSuscripcion)  {
        switch (tipoSuscripcion) {
            case DIARIA:
                return fechaInicio.plusDays(1);
            case SEMANAL:
                return fechaInicio.plusWeeks(1);
            case MENSUAL:
                return fechaInicio.plusMonths(1);
            case TRIMESTRAL:
                return fechaInicio.plusMonths(3);
            case SEMESTRAL:
                return fechaInicio.plusMonths(6);
            case ANUAL:
                return fechaInicio.plusYears(1);
            default:
                return null;
        }
    }

}

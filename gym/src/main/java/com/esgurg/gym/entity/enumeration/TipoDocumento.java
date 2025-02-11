package com.esgurg.gym.entity.enumeration;

public enum TipoDocumento {
    CEDULA("Cédula"),
    RUC("RUC"),
    PASAPORTE("Pasaporte");

    private final String tipoDocumento;

    TipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

}

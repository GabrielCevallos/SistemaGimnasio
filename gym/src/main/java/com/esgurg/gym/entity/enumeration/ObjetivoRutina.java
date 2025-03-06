package com.esgurg.gym.entity.enumeration;

public enum ObjetivoRutina {
    HIPERTROFIA("HIPERTROFIA"),
    FUERZA("FUERZA"),
    RESISTENCIA("RESISTENCIA"),
    POWERBUILDING("POWERBUILDING");

    private final String objetivoRutina;

    ObjetivoRutina(String objetivoRutina) {
        this.objetivoRutina = objetivoRutina;
    }

    public String getobjetivoRutina() {
        return objetivoRutina;
    }
    
}

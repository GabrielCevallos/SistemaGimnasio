package com.esgurg.gym.dto;

import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.Suscripcion;
import com.esgurg.gym.entity.enumeration.TipoSuscripcion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionDTO {
    private Long id;

    private TipoSuscripcion tipoSuscripcion;
    private Float precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Boolean activa;

    private Persona persona;

    public SuscripcionDTO(TipoSuscripcion tipoSuscripcion,
                          Float precio,
                          LocalDate fechaInicio,
                          LocalDate fechaFin,
                          Boolean activa,
                          Persona persona) {
        this.tipoSuscripcion = tipoSuscripcion;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.activa = activa;
        this.persona = persona;
    }

    public Suscripcion setValuesTo(Suscripcion suscripcion) {
        if (this.tipoSuscripcion != null) {
            suscripcion.setNombre(this.tipoSuscripcion);
        }
        if (this.precio != null) {
            suscripcion.setPrecio(this.precio);
        }
        if (this.fechaInicio != null) {
            suscripcion.setFechaInicio(this.fechaInicio);
        }
        if (this.fechaFin != null) {
            suscripcion.setFechaExpiracion(this.fechaFin);
        }
        if (this.activa != null) {
            suscripcion.setActiva(this.activa);
        }
        if (this.persona != null) {
            suscripcion.setPersona(this.persona);
        }
        return suscripcion;
    }

    public Map<String, Object> getEssentialInfo() {
        return Map.of(
                "id", this.id,
                "tipoSuscripcion", this.tipoSuscripcion,
                "precio", this.precio,
                "fechaInicio", this.fechaInicio,
                "fechaFin", this.fechaFin,
                "activa", this.activa,
                "persona", this.persona
        );
    }
}

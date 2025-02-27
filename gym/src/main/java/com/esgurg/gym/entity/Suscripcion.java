package com.esgurg.gym.entity;

import com.esgurg.gym.entity.enumeration.TipoSuscripcion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suscripcionId;

    private TipoSuscripcion tipo;
    private Float precio;

    private LocalDate fechaInicio;
    private LocalDate fechaExpiracion;

    private Boolean activa;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;


}

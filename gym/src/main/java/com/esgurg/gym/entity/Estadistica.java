package com.esgurg.gym.entity;

import com.esgurg.gym.entity.enumeration.ClasificacionImc;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Estadistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estadisticaId;

    @Column(nullable = false)
    private float medidaEspalda;

    @Column(nullable = false)
    private float medidaPierna;

    @Column(nullable = false)
    private float medidaBrazo;

    @Column(nullable = false)
    private float medidaCintura;

    @Column(nullable = false)
    private float medidaPecho;

    @Column(nullable = false)
    private float peso;

    @Column(nullable = false)
    private float altura;

    @Column(nullable = false)
    private ClasificacionImc clasificacion;

    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}

package com.esgurg.gym.entity;

import com.esgurg.gym.entity.enumeration.GrupoMuscularObjetivo;
import com.esgurg.gym.entity.enumeration.TipoEjercicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Sirve para poner getters y setters de forma automática
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ejercicioId;

    @Column(nullable = false, length = 90, unique = true)
    private String nombreEjercicio;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String videoUrl;

    @Column(nullable = false)
    private Float minTiempoDescanso;

    @Column(nullable = false)
    private Float maxTiempoDescanso;

    @Column(nullable = false)
    private int minNroSeries;

    @Column(nullable = false)
    private int maxNroSeries;

    @Column(nullable = false)
    private int minNroReps;

    @Column(nullable = false)
    private int maxNroReps;

    @Column(nullable = false)
    private TipoEjercicio tipoEjercicio;

    @Column(nullable = false)
    private GrupoMuscularObjetivo grupoMuscularObjetivo;
}

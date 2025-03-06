package com.esgurg.gym.entity;

import com.esgurg.gym.entity.enumeration.ObjetivoRutina;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rutinaId;

    @Column(nullable = false, length = 90, unique = true)
    private String nombreRutina;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private int nroEjercicios;

    @Column(nullable = false)
    private ObjetivoRutina objetivoRutina;

    @ManyToMany
    @JoinTable(name = "rutina_ejercicio",  // Nombre de la tabla intermedia
                joinColumns = @JoinColumn(name = "rutina_id"),  // Columna que hace referencia a la entidad Rutina
                inverseJoinColumns = @JoinColumn(name = "ejercicio_id"))
    private List<Ejercicio> ejercicios;
}

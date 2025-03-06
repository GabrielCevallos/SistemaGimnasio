package com.esgurg.gym.entity;

import com.esgurg.gym.entity.enumeration.Dia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long splitId;

    @Column(nullable = false, length = 90, unique = true)
    private String nombreSplit;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private int nroDias;

    @Column(nullable = false)
    private Dia dia;

    @ManyToMany
    @JoinTable(name = "split_rutina",  // Nombre de la tabla intermedia
                joinColumns = @JoinColumn(name = "split_id"),  // Columna que hace referencia a la entidad Split
                inverseJoinColumns = @JoinColumn(name = "rutina_id"))
    private List<Rutina> rutinas;

}

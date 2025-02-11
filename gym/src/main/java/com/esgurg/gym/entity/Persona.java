package com.esgurg.gym.entity;

import com.esgurg.gym.entity.enumeration.Genero;
import com.esgurg.gym.entity.enumeration.TipoDocumento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaId;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 10)
    private String telefonoCelular;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false, length = 6)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false, length = 13, unique = true)
    private String numeroDocumento;

    @Column(nullable = false)
    private Genero genero;
}

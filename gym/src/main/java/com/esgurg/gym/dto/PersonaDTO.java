package com.esgurg.gym.dto;

import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.enumeration.Genero;
import com.esgurg.gym.entity.enumeration.TipoDocumento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {
    private Long personaId;

    @NotNull(message = "El nombre es requerido")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+(?: [a-zA-ZÀ-ÿ]+)*$", message = "El nombre solo puede contener letras y espacios.")
    private String nombre;

    @NotNull(message = "El apellido es requerido")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ]+(?: [a-zA-ZÀ-ÿ]+)*$", message = "El apellido solo puede contener letras y espacios.")
    private String apellido;

    @NotNull(message = "La dirección es requerida")
    @Size(min = 10, max = 100, message = "La dirección debe tener entre 10 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9., ]+$", message = "La dirección solo puede contener letras, números, espacios, puntos y comas.")
    private String direccion;

    @NotNull(message = "El teléfono celular es requerido")
    @Size(min = 10, max = 10, message = "El teléfono celular debe tener 10 dígitos")
    @Pattern(regexp = "^[0-9]*$", message = "El teléfono celular solo puede contener números")
    private String telefonoCelular;

    @NotNull(message = "El tipo de documento es requerido")
    private TipoDocumento tipoDocumento;

    @NotNull(message = "La fecha de nacimiento es requerida")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El número de documento es requerido")
    private String numeroDocumento;

    @NotNull(message = "El género es requerido")
    private Genero genero;

    public PersonaDTO(Persona persona) {
        this.personaId = persona.getPersonaId();
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.direccion = persona.getDireccion();
        this.telefonoCelular = persona.getTelefonoCelular();
        this.tipoDocumento = persona.getTipoDocumento();
        this.fechaNacimiento = persona.getFechaNacimiento();
        this.numeroDocumento = persona.getNumeroDocumento();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class PersonaInfo {
        private Long personaId;
        private String nombreCompleto;
        private String documento;
    }

    public PersonaInfo getPersonaInfo() {
        return new PersonaInfo(
                personaId,
                nombre + " " + apellido,
                tipoDocumento.getTipoDocumento() + " " + numeroDocumento
        );
    }
}

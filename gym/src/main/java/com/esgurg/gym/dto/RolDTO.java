package com.esgurg.gym.dto;

import com.esgurg.gym.entity.security.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private Long id;
    @NotNull
    private String nombre;

    public RolDTO(Rol rol) {
        this.id = rol.getRolId();
        this.nombre = rol.getNombre();
    }

    public Rol setValuesTo(Rol ro) {
        if (this.id != null)
            ro.setRolId(this.id);
        if (this.nombre != null)
            ro.setNombre(this.nombre);
        return ro;
    }
}

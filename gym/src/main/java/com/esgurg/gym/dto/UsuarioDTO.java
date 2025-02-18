package com.esgurg.gym.dto;

import com.esgurg.gym.entity.Perfil;
import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.Usuario;
import com.esgurg.gym.entity.security.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;

    @NotNull(message = "El correo electrónico es requerido")
    @Email(message = "El correo electrónico no es válido")
    private String correoElectronico;

    @NotNull(message = "La contraseña es requerida")
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String contrasena;

    @NotNull(message = "El estado es requerido")
    private Boolean activo;

    @NotNull(message = "El rol es requerido")
    private Rol rol;

    @NotNull(message = "La persona es requerida")
    private Persona persona;

    @NotNull(message = "El perfil es requerido")
    private Perfil perfil;

    public Map<String, Object> essentialInfo() {
        return Map.of(
                "id", this.id,
                "correoElectronico", this.correoElectronico,
                "activo", this.activo,
                "rol", this.rol.getNombre()
        );
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getUsuarioId();
        this.correoElectronico = usuario.getCorreoElectronico();
        this.activo = usuario.getActivo();
        this.rol = usuario.getRol();
        this.persona = usuario.getPersona();
        this.perfil = usuario.getPerfil();
    }

    public Usuario setValuesTo(Usuario usuario) {
        if (this.id != null)
            usuario.setUsuarioId(this.id);
        if (this.correoElectronico != null)
            usuario.setCorreoElectronico(this.correoElectronico);
        if (this.contrasena != null)
            usuario.setContrasena(this.contrasena);
        if (this.activo != null)
            usuario.setActivo(this.activo);
        if (this.rol != null)
            usuario.setRol(this.rol);
        if (this.persona != null)
            usuario.setPersona(this.persona);
       if (this.perfil != null)
            usuario.setPerfil(this.perfil);
        return usuario;
    }

}

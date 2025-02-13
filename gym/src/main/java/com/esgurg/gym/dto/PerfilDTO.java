package com.esgurg.gym.dto;

import com.esgurg.gym.entity.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Long id;
    private String nickname;
    private String objetivos;
    private LocalDate fechaRegistro;
    private String imagen;

    public PerfilDTO(Perfil perfil) {
        this.id = perfil.getPerfilId();
        this.nickname = perfil.getNickname();
        this.objetivos = perfil.getObjetivos();
        this.fechaRegistro = perfil.getFechaRegistro();
        this.imagen = perfil.getImagen();
    }

    public Perfil setValuesTo(Perfil pe) {
        if (this.id != null)
            pe.setPerfilId(this.id);
        if (this.nickname != null)
            pe.setNickname(this.nickname);
        if (this.objetivos != null)
            pe.setObjetivos(this.objetivos);
        if (this.fechaRegistro != null)
            pe.setFechaRegistro(this.fechaRegistro);
        if (this.imagen != null)
            pe.setImagen(this.imagen);
        return pe;
    }

    public Map<String, Object> getEssentialInfo() {
        return Map.of(
                "id", this.id,
                "nickname", this.nickname,
                "objetivos", this.objetivos,
                "fechaRegistro", this.fechaRegistro,
                "imagen", this.imagen
        );
    }

}





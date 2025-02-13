package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.PerfilDTO;
import com.esgurg.gym.service.PerfilService;
import com.esgurg.gym.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/perfil")
public class PerfilRestController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPerfilList() {
        return new ResponseBuilder().responseWithOperation(
                perfilService::findAll,
                "Perfiles encontrados",
                HttpStatus.OK,
                "Error al intentar encontrar los perfiles",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/{nickname}")
    public ResponseEntity<Map<String, Object>> findPerfil(@PathVariable String nickname) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    PerfilDTO perfil = new PerfilDTO(
                            perfilService.findByNickname(nickname).orElseThrow(() -> new RuntimeException("Perfil no encontrado"))
                    );
                    return perfil.getEssentialInfo();
                },
                "Perfil encontrado",
                HttpStatus.OK,
                "Error al intentar encontrar el perfil",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> findPerfil(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    PerfilDTO perfil = new PerfilDTO(
                            perfilService.findById(id).orElseThrow(() -> new RuntimeException("Perfil no encontrado"))
                    );
                    return perfil.getEssentialInfo();
                },
                "Perfil encontrado",
                HttpStatus.OK,
                "Error al intentar encontrar el perfil",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> updatePerfil(@RequestBody PerfilDTO perfil) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    perfilService.update(perfil);
                    return perfil.getEssentialInfo();
                },
                "Perfil actualizado",
                HttpStatus.OK,
                "Error al intentar actualizar el perfil",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}

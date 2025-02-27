package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.ChangePasswordDTO;
import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.service.UsuarioService;
import com.esgurg.gym.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsuarioList() {
        return new ResponseBuilder().responseWithOperation(
                usuarioService::findAll,
                "Usuarios encontrados",
                HttpStatus.OK,
                "Error al intentar encontrar los usuarios",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Map<String, Object>> findUsuario(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    return usuarioService.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                },
                "Usuario encontrado",
                HttpStatus.OK,
                "Error al intentar encontrar el usuario",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<Map<String, Object>> findUsuario(String email) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    return usuarioService.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                },
                "Usuario encontrado",
                HttpStatus.OK,
                "Error al intentar encontrar el usuario",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/role/{role}")
    public ResponseEntity<Map<String, Object>> findUsuarioByRole(@PathVariable String role) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    return usuarioService.findByRole(role);
                },
                "Usuarios encontrados",
                HttpStatus.OK,
                "Error al intentar encontrar los usuarios",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> updateUsuario(@RequestBody UsuarioDTO usuario) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    usuarioService.update(usuario);
                    return true;
                },
                "Usuario actualizado",
                HttpStatus.OK,
                "Error al intentar actualizar el usuario",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUsuario(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    usuarioService.delete(id);
                    return true;
                },
                "Usuario dado de baja",
                HttpStatus.OK,
                "Error al intentar eliminar el usuario",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    usuarioService.changePassword(changePasswordDTO);
                    return true;
                },
                "Contraseña cambiada",
                HttpStatus.OK,
                "Error al intentar cambiar la contraseña",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}


package com.esgurg.gym.service;

import com.esgurg.gym.dto.*;
import com.esgurg.gym.entity.Perfil;
import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.Usuario;
import com.esgurg.gym.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SuscripcionService suscripcionService;
    private final PersonaService personaService;
    private final RolService rolService;
    private final PerfilService perfilService;

    public void saveCreatingPerfil(UsuarioDTO usuarioDTO) {
        final int number = (int)Math.round(Math.random()*1000);
        usuarioDTO.setPerfil(
                new Perfil(
                        null,
                        usuarioDTO.getPersona().getNombre().toLowerCase() + number,
                        "Ingrese aquí los objetivos que desea alcanzar",
                        LocalDate.now(),
                        "user.png"
                )
        );
        save(usuarioDTO);
    }

    public void save(UsuarioDTO usuario) {
        Usuario usuarioToSave = usuario.setValuesTo(new Usuario());

        if (usuario.getRol().getNombre() != null) {
            String rolName = usuario.getRol().getNombre();
            if (rolService.findByNombre(rolName).isEmpty()) {
                rolService.save(new RolDTO(usuario.getRol()));
            }
            usuarioToSave.setRol(rolService.findByNombre(rolName).get());
        }

        if (usuario.getPerfil().getPerfilId() == null) {
            perfilService.save(new PerfilDTO(usuario.getPerfil()));
            Perfil perfil = perfilService.findByNickname(usuario.getPerfil().getNickname())
                    .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
            usuarioToSave.setPerfil(perfil);
        }

        if (usuario.getPersona().getPersonaId() == null) {
            personaService.save(new PersonaDTO(usuario.getPersona()));
            Persona persona = personaService.findByNumeroDocumento(usuario.getPersona().getNumeroDocumento())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"));
            usuarioToSave.setPersona(persona);
            suscripcionService.saveSuscripcionOf(persona);
        }
        usuarioRepository.save(usuarioToSave);
    }

    public void update(UsuarioDTO usuario) {
        Usuario currentUser = usuarioRepository.findById(usuario.getUsuarioId()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        Usuario usuarioToUpdate = usuario.setValuesTo(currentUser);
        usuarioRepository.save(usuarioToUpdate);
    }

    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String email = changePasswordDTO.getEmail();
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();

        Usuario usuario = usuarioRepository.findByCorreoElectronico(email).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        if (!passwordEncoder.matches(oldPassword, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        usuario.setContrasena(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return Optional.of(
                usuarioRepository.findByCorreoElectronico(email).orElseThrow(
                    () -> new RuntimeException("Usuario no encontrado")
                )
        );
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findByRole(String role) {
        Boolean active_users = true;
        return usuarioRepository.findByActivoAndRol(
                active_users,
                rolService.findByNombre(role)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado"))
        );
    }

}

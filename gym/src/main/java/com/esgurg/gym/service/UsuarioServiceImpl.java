package com.esgurg.gym.service;

import com.esgurg.gym.dto.PerfilDTO;
import com.esgurg.gym.dto.PersonaDTO;
import com.esgurg.gym.dto.RolDTO;
import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.entity.Usuario;
import com.esgurg.gym.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaService personaService;
    private final RolService rolService;
    private final PerfilService perfilService;

    @Override
    public void save(UsuarioDTO usuario) {
        Usuario usuarioToSave = usuario.setValuesTo(new Usuario());
        if (usuario.getRol().getRolId() == null) {
            rolService.save(new RolDTO(usuario.getRol()));
            usuarioToSave.setRol(rolService.findByNombre(usuario.getRol().getNombre()).get());
        }
        if (usuario.getPerfil().getPerfilId() == null) {
            perfilService.save(new PerfilDTO(usuario.getPerfil()));
            usuarioToSave.setPerfil(perfilService.findByNickname(usuario.getPerfil().getNickname()).get());
        }
        if (usuario.getPersona().getPersonaId() == null) {
            personaService.save(new PersonaDTO(usuario.getPersona()));
            usuarioToSave.setPersona(personaService.findByNumeroDocumento(usuario.getPersona().getNumeroDocumento()).get());
        }
        usuarioRepository.save(usuarioToSave);
    }

    @Override
    public void update(UsuarioDTO usuario) {
        Usuario currentUser = usuarioRepository.findById(usuario.getId()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        Usuario usuarioToUpdate = usuario.setValuesTo(currentUser);
        usuarioRepository.save(usuarioToUpdate);
    }

    @Override
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void changePassword(String email, String password) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(email).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        usuario.setContrasena(password);
        usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return Optional.of(
                usuarioRepository.findByCorreoElectronico(email).orElseThrow(
                    () -> new RuntimeException("Usuario no encontrado")
                )
        );
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

}

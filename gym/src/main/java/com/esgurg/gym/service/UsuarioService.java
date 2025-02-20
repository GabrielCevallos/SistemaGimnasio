package com.esgurg.gym.service;

import com.esgurg.gym.dto.ChangePasswordDTO;
import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    void save(UsuarioDTO usuario);
    void saveCreatingPerfil(UsuarioDTO usuarioDTO);
    void update(UsuarioDTO usuario);
    void delete(Long id);
    void changePassword(ChangePasswordDTO changePasswordDTO);
    List<Usuario> findAll();
    List<Usuario> findByRole(String role);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
}

package com.esgurg.gym.service;

import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    void save(UsuarioDTO usuario);
    void update(UsuarioDTO usuario);
    void delete(Long id);
    void changePassword(String email, String password);
    List<Usuario> findAll();
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);

}

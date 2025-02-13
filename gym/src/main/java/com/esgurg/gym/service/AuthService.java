package com.esgurg.gym.service;

import com.esgurg.gym.dto.TokenResponseDTO;
import com.esgurg.gym.dto.UsuarioDTO;

public interface AuthService {
    TokenResponseDTO register(UsuarioDTO usuario);
    TokenResponseDTO login(UsuarioDTO usuario);
    TokenResponseDTO refresh(final String authHeader);
}

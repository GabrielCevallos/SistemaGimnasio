package com.esgurg.gym.service;

import com.esgurg.gym.entity.Usuario;

import java.util.Date;

public interface JwtService {
    String createToken(Usuario usuario);
    String createRefreshToken(Usuario usuario);
    String extractUserEmail(String token);
    boolean isValid(String token, Usuario usuario);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
}

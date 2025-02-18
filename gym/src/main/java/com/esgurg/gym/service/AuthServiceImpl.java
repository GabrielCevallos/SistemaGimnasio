package com.esgurg.gym.service;

import com.esgurg.gym.dto.TokenResponseDTO;
import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.entity.Usuario;
import com.esgurg.gym.entity.security.Token;
import com.esgurg.gym.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO register(UsuarioDTO usuario) {
        String contrasena = usuario.getContrasena();
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuarioService.save(usuario);

        Usuario user = usuarioService.findByEmail(usuario.getCorreoElectronico())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.createToken(user);
        String refreshToken = jwtService.createRefreshToken(user);
        saveUserToken(user, token);
        return new TokenResponseDTO(token, refreshToken);
    }

    public TokenResponseDTO login(UsuarioDTO usuario) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getCorreoElectronico(),
                        usuario.getContrasena()
                )
        );
        Usuario user = usuarioService.findByEmail(usuario.getCorreoElectronico())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var token = jwtService.createToken(user);
        var RefreshToken = jwtService.createRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return new TokenResponseDTO(token, RefreshToken);
    }

    public TokenResponseDTO refresh(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Bearer Token");
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUserEmail(refreshToken);

        if (userEmail == null) {
            throw new RuntimeException("Invalid Token");
        }

        final Usuario usuario = usuarioService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!jwtService.isValid(refreshToken, usuario)) {
            throw new RuntimeException("Invalid Token");
        }

        final String accesToken = jwtService.createToken(usuario);
        revokeAllUserTokens(usuario);
        saveUserToken(usuario, accesToken);
        return new TokenResponseDTO(accesToken, refreshToken);
    }

    public void saveUserToken(Usuario usuario, String jwtToken) {
        Token token = Token.builder()
                .usuario(usuario)
                .tokenType(Token.TokenType.BEARER)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(final Usuario user) {
        final List<Token> validUserTokens = tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByUsuario(user);
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
                tokenRepository.save(token);
            });
        }
    }
}

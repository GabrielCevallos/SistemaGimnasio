package com.esgurg.gym.service;

import com.esgurg.gym.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.SIG.HS384;

@Service
public class JwtServiceImpl implements JwtService {

    public final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    public final Long TOKEN_EXPIRATION = 86400000L; // 1 day
    public final Long REFRESH_TOKEN_EXPIRATION = 604800000L; // 7 days

    @Override
    public String createToken(Usuario usuario) {
        return buildToken(usuario, TOKEN_EXPIRATION);
    }

    @Override
    public String createRefreshToken(Usuario usuario) {
        return buildToken(usuario, REFRESH_TOKEN_EXPIRATION);
    }

    @Override
    public String extractUserEmail(String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    @Override
    public boolean isValid(String token, final Usuario usuario) {
        final String userEmail = extractUserEmail(token);
        return (userEmail.equals(usuario.getCorreoElectronico()) && !isTokenExpired(token));
    }

    @Override
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String buildToken(Usuario usuario, final long expiration) {
        return Jwts.builder()
                .id(usuario.getUsuarioId().toString())
                .claims(Map.of("name", usuario.getPersona().getNombre()))
                .subject(usuario.getCorreoElectronico())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

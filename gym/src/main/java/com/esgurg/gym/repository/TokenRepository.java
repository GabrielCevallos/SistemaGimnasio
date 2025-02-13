package com.esgurg.gym.repository;

import com.esgurg.gym.entity.Usuario;
import com.esgurg.gym.entity.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllValidIsFalseOrRevokedIsFalseByUsuario(Usuario user);
    Optional<Token> findByToken(String token);
}

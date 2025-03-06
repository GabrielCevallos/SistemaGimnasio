package com.esgurg.gym.entity.security;

import com.esgurg.gym.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    public enum TokenType {
        ACCESS, REFRESH, BEARER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long tokenId;

    public String token;

    @Default
    public TokenType tokenType = TokenType.BEARER;

    @Default
    public Boolean revoked = false;

    @Default
    public Boolean expired = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;

}

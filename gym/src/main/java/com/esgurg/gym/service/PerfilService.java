package com.esgurg.gym.service;

import com.esgurg.gym.dto.PerfilDTO;
import com.esgurg.gym.entity.Perfil;

import java.util.List;
import java.util.Optional;

public interface PerfilService {
    void save(PerfilDTO perfil);
    void update(PerfilDTO perfil);
    List<Perfil> findAll();
    Optional<Perfil> findById(Long perfilId);
    Optional<Perfil> findByNickname(String nickname);
}

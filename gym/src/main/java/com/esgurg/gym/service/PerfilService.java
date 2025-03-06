package com.esgurg.gym.service;

import com.esgurg.gym.dto.PerfilDTO;
import com.esgurg.gym.entity.Perfil;
import com.esgurg.gym.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public void save(PerfilDTO perfil) {
        Perfil perfilToSave = perfil.setValuesTo(new Perfil());
        perfilRepository.save(perfilToSave);
    }

    public void update(PerfilDTO perfil) {
        Perfil perfilToUpdate = perfil.setValuesTo(
                perfilRepository.findById(perfil.getId()).orElseThrow(
                        () -> new RuntimeException("Perfil no encontrado")
                )
        );
        perfilRepository.save(perfilToUpdate);
    }

    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> findById(Long perfilId) {
        return Optional.of(perfilRepository.findById(perfilId).orElseThrow(() -> new RuntimeException("Perfil no encontrado")));
    }

    public Optional<Perfil> findByNickname(String nickname) {
        return Optional.of(perfilRepository.findByNickname(nickname).orElseThrow(() -> new RuntimeException("Perfil no encontrado")));
    }

}

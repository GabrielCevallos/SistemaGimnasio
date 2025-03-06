package com.esgurg.gym.service;

import com.esgurg.gym.dto.RolDTO;
import com.esgurg.gym.entity.security.Rol;
import com.esgurg.gym.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public void save(RolDTO rol) {
        Rol rolToSave = rol.setValuesTo(new Rol());
        rolRepository.save(rolToSave);
    }

    public void update(RolDTO rol) {
        Rol rolToupdate = rol.setValuesTo(
                rolRepository.findById(rol.getId()).orElseThrow(
                        () -> new RuntimeException("Rol no encontrado")
                )
        );
        rolRepository.save(rolToupdate);
    }

    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

}

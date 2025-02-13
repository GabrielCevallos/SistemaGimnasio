package com.esgurg.gym.service;

import com.esgurg.gym.dto.RolDTO;
import com.esgurg.gym.entity.security.Rol;

import java.util.Optional;

public interface RolService {
    void save(RolDTO rol);
    void update(RolDTO rol);
    Optional<Rol> findByNombre(String nombre);
}

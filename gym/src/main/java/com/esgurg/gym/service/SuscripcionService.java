package com.esgurg.gym.service;

import com.esgurg.gym.dto.SuscripcionDTO;
import com.esgurg.gym.entity.Suscripcion;

import java.util.List;
import java.util.Optional;

public interface SuscripcionService {
    void save(SuscripcionDTO suscripcionDTO);
    void update(SuscripcionDTO suscripcionDTO);
    Optional<Suscripcion> renovarSuscripcion(SuscripcionDTO suscripcionDTO);
    Optional<Suscripcion> cancelarSuscripcion(Long id);
    List<Suscripcion> findAll();
    List<Suscripcion> findByActiva(Boolean activa);
}

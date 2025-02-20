package com.esgurg.gym.repository;

import com.esgurg.gym.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByActiva(Boolean activa);
    Optional<Suscripcion> findByPersona_NumeroDocumento(String numeroDocumento);
}

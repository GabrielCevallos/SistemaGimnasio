package com.esgurg.gym.repository;

import com.esgurg.gym.entity.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByNombreEjercicioContainingIgnoreCase(String nombre);
    Optional<Ejercicio> findByNombreEjercicio(String nombre);
}

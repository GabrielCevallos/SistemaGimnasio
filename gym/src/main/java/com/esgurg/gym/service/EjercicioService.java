package com.esgurg.gym.service;

import com.esgurg.gym.dto.EjercicioDTO;
import com.esgurg.gym.entity.Ejercicio;
import com.esgurg.gym.repository.EjercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    public void save(EjercicioDTO ejercicio) {
        Ejercicio ejercicioToSave = ejercicio.setValuesTo(new Ejercicio());
        ejercicioRepository.save(ejercicioToSave);
    }

    public void update(EjercicioDTO ejercicio) {
        Ejercicio ejercicioToUpdate = ejercicio.setValuesTo(
                ejercicioRepository.findById(ejercicio.getEjercicioId()).orElseThrow(
                        () -> new RuntimeException("Ejercicio no encontrado")
                )
        );
        ejercicioRepository.save(ejercicioToUpdate);
    }

    public List<Ejercicio> findAll() {
        return ejercicioRepository.findAll();
    }

    public Optional<Ejercicio> findByNombre(String nombre) {
        return Optional.of(ejercicioRepository.findByNombreEjercicio(nombre).orElseThrow(() -> new RuntimeException("Ejercicio no encontrado")));
    }

    public void delete(Long id) {
        Optional<Ejercicio> ejercicio = ejercicioRepository.findById(id);
        ejercicio.ifPresent(ejercicioRepository::delete);
    }
}

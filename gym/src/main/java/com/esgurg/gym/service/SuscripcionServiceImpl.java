package com.esgurg.gym.service;

import com.esgurg.gym.dto.SuscripcionDTO;
import com.esgurg.gym.entity.Suscripcion;
import com.esgurg.gym.entity.enumeration.TipoSuscripcion;
import com.esgurg.gym.repository.SuscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;

    public void save(SuscripcionDTO suscripcionDTO) {
        Suscripcion suscripcionToSave = suscripcionDTO.setValuesTo(new Suscripcion());
        suscripcionRepository.save(suscripcionToSave);
    }

    public void update(SuscripcionDTO suscripcionDTO) {
        Suscripcion suscripcionToUpdate = suscripcionRepository.findById(suscripcionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Suscripcion not found"));
        suscripcionToUpdate = suscripcionDTO.setValuesTo(suscripcionToUpdate);
        suscripcionRepository.save(suscripcionToUpdate);
    }

    public List<Suscripcion> findAll() {
        return suscripcionRepository.findAll();
    }

    public List<Suscripcion> findByActiva(Boolean activa) {
        return suscripcionRepository.findByActiva(activa);
    }

    public Optional<Suscripcion> renovarSuscripcion(SuscripcionDTO suscripcionDTO) {
        Suscripcion suscripcion = suscripcionRepository.findById(suscripcionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Suscripcion not found"));

        suscripcion.setActiva(true);
        suscripcion.setFechaInicio(LocalDate.now());
        final LocalDate expiration = TipoSuscripcion.getExpirationDateOf(
                suscripcionDTO.getFechaInicio(),
                suscripcionDTO.getTipoSuscripcion()
        );
        suscripcion.setFechaExpiracion(expiration);
        suscripcion.setPrecio(suscripcionDTO.getTipoSuscripcion().getPrecio());
        suscripcionRepository.save(suscripcion);
        return Optional.of(suscripcionRepository.save(suscripcion));
    }

    public Optional<Suscripcion> cancelarSuscripcion(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripcion not found"));
        suscripcion.setActiva(false);
        suscripcionRepository.save(suscripcion);
        return Optional.of(suscripcionRepository.save(suscripcion));
    }

}

package com.esgurg.gym.service;

import com.esgurg.gym.dto.SuscripcionDTO;
import com.esgurg.gym.entity.Persona;
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

    @Override
    public void save(SuscripcionDTO suscripcionDTO) {
        Suscripcion suscripcionToSave = suscripcionDTO.setValuesTo(new Suscripcion());
        suscripcionRepository.save(suscripcionToSave);
    }

    @Override
    public void saveSuscripcionOf(Persona persona) {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setActiva(true);
        suscripcion.setFechaInicio(LocalDate.now());
        suscripcion.setTipo(TipoSuscripcion.DIARIA);
        int DAYS_OF_TRIAL = 1;
        suscripcion.setFechaExpiracion(LocalDate.now().plusDays(DAYS_OF_TRIAL));
        float PRICE_OF_TRIAL = TipoSuscripcion.DIARIA.getPrecio();
        suscripcion.setPrecio(PRICE_OF_TRIAL);
        LocalDate expiration = TipoSuscripcion.getExpirationDateOf(LocalDate.now(), TipoSuscripcion.DIARIA);
        suscripcion.setFechaExpiracion(expiration);
        suscripcion.setPersona(persona);

        suscripcionRepository.save(suscripcion);
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

    public Optional<Suscripcion> findByPersonaNumeroDocumento(String numeroDocumento) {
        return suscripcionRepository.findByPersona_NumeroDocumento(numeroDocumento);
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
                suscripcionDTO.getTipo()
        );
        suscripcion.setFechaExpiracion(expiration);
        suscripcion.setPrecio(suscripcionDTO.getTipo().getPrecio());
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

package com.esgurg.gym.service;

import com.esgurg.gym.dto.PersonaDTO;
import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.enumeration.TipoDocumento;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonaService {
    void save(PersonaDTO persona);
    void update(PersonaDTO persona);
    List<Persona> findAll();
    Optional<Persona> findById(Long personaId);
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
    List<Persona> findByNombre(String nombre);
    List<Persona> findByApellido(String apellido);
    List<Persona> findByTipoDocumento(TipoDocumento tipoDocumento);
    List<Persona> findByTelefonoCelular(String telefonoCelular);
    List<Persona> findByDireccion(String direccion);
    List<Persona> sortByAttribute(String attribute);
    Map<String, Object> enumerations();
}

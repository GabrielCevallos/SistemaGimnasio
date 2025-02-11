package com.esgurg.gym.repository;

import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.enumeration.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
    List<Persona> findByNombre(String nombre);
    List<Persona> findByApellido(String apellido);
    List<Persona> findByTipoDocumento(TipoDocumento tipoDocumento);
    List<Persona> findByTelefonoCelular(String telefonoCelular);
    List<Persona> findByDireccion(String direccion);
}

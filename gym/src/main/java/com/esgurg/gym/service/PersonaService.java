package com.esgurg.gym.service;

import com.esgurg.gym.dto.PersonaDTO;
import com.esgurg.gym.dto.validation.Validator;
import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.enumeration.Genero;
import com.esgurg.gym.repository.PersonaRepository;
import com.esgurg.gym.entity.enumeration.TipoDocumento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    public void save(PersonaDTO persona) {
        Persona personaToSave = persona.setValuesTo(new Persona());

        Validator.validateDoc(personaToSave);

        personaRepository.save(personaToSave);
    }

    public void update(PersonaDTO persona) {
        Persona personaToUpdate = personaRepository.findById(persona.getPersonaId()).orElseThrow(
                () -> new RuntimeException("Persona no encontrada")
        );
        personaToUpdate = persona.setValuesTo(personaToUpdate);

        Validator.validateDoc(personaToUpdate);

        @Valid Persona personaValidated = personaToUpdate;
        personaRepository.save(personaValidated);
    }

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Optional<Persona> findById(Long personaId) {
        return personaRepository.findById(personaId);
    }

    public Optional<Persona> findByNumeroDocumento(String numeroDocumento) {
        return personaRepository.findByNumeroDocumento(numeroDocumento);
    }

    public List<Persona> findByNombre(String nombre) {
        return personaRepository.findByNombre(nombre);
    }

    public List<Persona> findByApellido(String apellido) {
        return personaRepository.findByApellido(apellido);
    }

    public List<Persona> findByTipoDocumento(TipoDocumento tipoDocumento) {
        return personaRepository.findByTipoDocumento(tipoDocumento);
    }

    public List<Persona> findByTelefonoCelular(String telefonoCelular) {
        return personaRepository.findByTelefonoCelular(telefonoCelular);
    }

    public List<Persona> findByDireccion(String direccion) {
        return personaRepository.findByDireccion(direccion);
    }

    public List<PersonaDTO> findAllDTO() {
        List<Persona> personas = personaRepository.findAll();
        return personas.stream().map(PersonaDTO::new).toList();
    }

    public List<Persona> sortByAttribute(String attribute) {
        List<Persona> personas = personaRepository.findAll();
        switch (attribute) {
            case "nombre":
                personas.sort((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
                break;
            case "apellido":
                personas.sort((p1, p2) -> p1.getApellido().compareTo(p2.getApellido()));
                break;
            case "tipoDocumento":
                personas.sort((p1, p2) -> p1.getTipoDocumento().compareTo(p2.getTipoDocumento()));
                break;
            case "numeroDocumento":
                personas.sort((p1, p2) -> p1.getNumeroDocumento().compareTo(p2.getNumeroDocumento()));
                break;
            case "telefonoCelular":
                personas.sort((p1, p2) -> p1.getTelefonoCelular().compareTo(p2.getTelefonoCelular()));
                break;
            case "direccion":
                personas.sort((p1, p2) -> p1.getDireccion().compareTo(p2.getDireccion()));
                break;
            default:
                throw new RuntimeException("Atributo no válido");
        }
        return personas;
    }

    public Map<String, Object> enumerations() {
        return Map.of(
                "tipoDocumento", TipoDocumento.values(),
                "genero", Genero.values()
        );
    }
}

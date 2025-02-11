package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.PersonaDTO;
import com.esgurg.gym.entity.enumeration.TipoDocumento;
import com.esgurg.gym.service.PersonaService;
import com.esgurg.gym.utils.ResponseBuilder;
import com.esgurg.gym.utils.ResponseMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/persona")
public class PersonaRestController {

    private final PersonaService personaService;

    // DEPENDENCY INJECTION
    public PersonaRestController(PersonaService personaService) {
        this.personaService = personaService;
    }

    //ENDPOINTS
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPersonaList() {
        return new ResponseBuilder().responseWithOperation(
                // Try this
                personaService::findAll,
                ResponseMessages.PERSONA_DELETED_SUCCESS,
                HttpStatus.OK,
                // Or else
                ResponseMessages.PERSONA_LIST_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> savePersona(@Validated @RequestBody PersonaDTO persona) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    personaService.save(persona);
                    return new PersonaDTO(
                            personaService.findByNumeroDocumento(persona.getNumeroDocumento())
                                    .orElseThrow(() -> new RuntimeException(ResponseMessages.PERSONA_NOT_FOUND))
                    ).getPersonaInfo();
                },
                ResponseMessages.PERSONA_SAVED_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> updatePersona(@RequestBody PersonaDTO persona) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    personaService.update(persona);
                    return persona.getPersonaInfo();
                },
                ResponseMessages.PERSONA_UPDATED_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_LIST_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePersona(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    PersonaDTO personaDTO = new PersonaDTO(
                            personaService.findById(id)
                                    .orElseThrow(() -> new RuntimeException(ResponseMessages.PERSONA_NOT_FOUND))
                    );
                    personaService.delete(id);
                    return personaDTO.getPersonaInfo();
                },
                ResponseMessages.PERSONA_DELETED_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Map<String, Object>> findPersona(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findById(id),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/find/document/{document}")
    public ResponseEntity<Map<String, Object>> findPersonaByDocument(@PathVariable String document) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findByNumeroDocumento(document),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/find/name/{name}")
    public ResponseEntity<Map<String, Object>> findPersonaByName(@PathVariable String name) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findByNombre(name),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/find/surname/{surname}")
    public ResponseEntity<Map<String, Object>> findPersonaBySurname(@PathVariable String surname) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findByApellido(surname),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/find/doctype/{doctype}")
    public ResponseEntity<Map<String, Object>> findPersonaByDocType(@PathVariable TipoDocumento doctype) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findByTipoDocumento(doctype),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/find/phone/{phone}")
    public ResponseEntity<Map<String, Object>> findPersonaByPhone(@PathVariable String phone) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findByTelefonoCelular(phone),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/find/address/{address}")
    public ResponseEntity<Map<String, Object>> findPersonaByAddress(@PathVariable String address) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.findByDireccion(address),
                ResponseMessages.PERSONA_FOUND_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/sort/{attribute}")
    public ResponseEntity<Map<String, Object>> sortPersonaByAttribute(@PathVariable String attribute) {
        return new ResponseBuilder().responseWithOperation(
                () -> personaService.sortByAttribute(attribute),
                ResponseMessages.PERSONA_LIST_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_LIST_ERROR,
                HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("/enumerations")
    public ResponseEntity<Map<String, Object>> enumerations() {
        return new ResponseBuilder().responseWithOperation(
                personaService::enumerations,
                ResponseMessages.PERSONA_LIST_SUCCESS,
                HttpStatus.OK,
                ResponseMessages.PERSONA_LIST_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

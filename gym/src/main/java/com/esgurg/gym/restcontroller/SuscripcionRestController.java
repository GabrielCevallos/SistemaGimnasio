package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.SuscripcionDTO;
import com.esgurg.gym.service.SuscripcionService;
import com.esgurg.gym.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suscripcion")
public class SuscripcionRestController {

    private final SuscripcionService suscripcionService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findSuscripcionList() {
        return new ResponseBuilder().responseWithOperation(
                suscripcionService::findAll,
                "Suscripciones encontradas",
                HttpStatus.OK,
                "Error al obtener la lista de suscripciones",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/activas")
    public ResponseEntity<Map<String, Object>> findSuscripcionListActiva() {
        return new ResponseBuilder().responseWithOperation(
                () -> suscripcionService.findByActiva(true),
                "Suscripciones activas encontradas",
                HttpStatus.OK,
                "Error al obtener la lista de suscripciones activas",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PatchMapping("/renovar")
    public ResponseEntity<Map<String, Object>> renovarSuscripcion(@RequestBody SuscripcionDTO suscripcionDTO) {
        return new ResponseBuilder().responseWithOperation(
                () -> suscripcionService.renovarSuscripcion(suscripcionDTO),
                "Suscripción renovada",
                HttpStatus.OK,
                "Error al renovar la suscripción",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<Map<String, Object>> cancelarSuscripcion(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> suscripcionService.cancelarSuscripcion(id),
                "Suscripción cancelada",
                HttpStatus.OK,
                "Error al cancelar la suscripción",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/persona/{numeroDocumento}")
    public ResponseEntity<Map<String, Object>> findSuscripcionByPersona(@PathVariable String numeroDocumento) {
        return new ResponseBuilder().responseWithOperation(
                () -> suscripcionService.findByPersonaNumeroDocumento(numeroDocumento),
                "Suscripciones encontradas",
                HttpStatus.OK,
                "Error al obtener la lista de suscripciones",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}

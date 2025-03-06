package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.EjercicioDTO;
import com.esgurg.gym.service.EjercicioService;
import com.esgurg.gym.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ejercicio")
public class EjercicioRestController {

    private final EjercicioService ejercicioService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveEjercicio(@RequestBody EjercicioDTO ejercicio) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    ejercicioService.save(ejercicio);
                    return true;
                },
                "Ejercicio guardado",
                HttpStatus.OK,
                "Error al intentar guardar el ejercicio",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getEjercicioList() {
        return new ResponseBuilder().responseWithOperation(
                ejercicioService::findAll,
                "Ejercicios encontrados",
                HttpStatus.OK,
                "Error al intentar encontrar los ejercicios",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/find/{nombre}")
    public ResponseEntity<Map<String, Object>> findEjercicio(@PathVariable String nombre) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    EjercicioDTO ejercicio = new EjercicioDTO(
                            ejercicioService.findByNombre(nombre).orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"))
                    );
                    return ejercicio.getEssentialInfo();
                },
                "Ejercicio encontrado",
                HttpStatus.OK,
                "Error al intentar encontrar el ejercicio",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEjercicio(@PathVariable Long id) {
        return new ResponseBuilder().responseWithOperation(
                () -> {
                    ejercicioService.delete(id);
                    return true;
                },
                "Ejercicio eliminado",
                HttpStatus.OK,
                "Error al intentar eliminar el ejercicio",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

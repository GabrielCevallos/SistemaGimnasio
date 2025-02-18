package com.esgurg.gym.config;

import com.esgurg.gym.utils.ResponseBuilder;
import org.hibernate.NonUniqueResultException;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar errores de base de datos, como la violación de restricciones de unicidad
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDatabaseException(DataIntegrityViolationException ex) {
        // Devolver un mensaje adecuado con un código de estado 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error de base de datos: " + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Devolver un mensaje adecuado con un código de estado 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error de argumento: " + ex.getMessage());
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<Map<String, Object>> handleNonUniqueResultException(NonUniqueResultException ex) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withOperationStatus("Error")
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withOperationStatus("Error desconocido")
                .withMessage(ex.getMessage())
                .build();
    }


}

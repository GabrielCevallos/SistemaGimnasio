package com.esgurg.gym.config;

import com.esgurg.gym.utils.ResponseBuilder;
import org.hibernate.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(DataIntegrityViolationException ex) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withOperationStatus("Error de integridad de datos")
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withOperationStatus("Error")
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<Map<String, Object>> handleNonUniqueResultException(NonUniqueResultException ex) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withOperationStatus("Error")
                .withMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.FORBIDDEN)
                .withOperationStatus("Error de autorización")
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

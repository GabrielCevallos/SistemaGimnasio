package com.esgurg.gym.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private final Map<String, Object> responseBody;
    private HttpStatus status;
    private RepositoryOperation operation;

    public ResponseBuilder() {
        this.responseBody = new HashMap<>();
    }

    public void safeAdd(String k, Object v) {
        if (v != null && k != null) { // Add only if key and value are not null
            responseBody.put(k,v);
        } else {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
    }

    // Use methods in this order
    public ResponseBuilder withStatus(HttpStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
        return this;
    }

    public ResponseBuilder withData(Object data) {
        safeAdd("data",data);
        return this;
    }

    public ResponseBuilder withMessage(String message) {
        safeAdd("message", message);
        return this;
    }

    public ResponseBuilder withOperationStatus(String operationStatus) {
        safeAdd("operationStatus", operationStatus);
        return this;
    }

    public ResponseBuilder withError(String error) {
        safeAdd("error", error);
        return this;
    }

    private void withOperation(RepositoryOperation operation) {
        if(operation != null) {
            this.operation = operation;
        } else {
            throw new IllegalArgumentException("Operation cannot be null");
        }
    }

    public ResponseEntity<Map<String,Object>> build() {
        safeAdd("status", status.value());
        return ResponseEntity.status(this.status).body(responseBody);
    }

    public ResponseEntity<Map<String,Object>> buildWithOperation() {
        if (this.operation != null) {
            Object data = this.operation.execute();
            safeAdd("data", data);
        }

        return build();
    }

    public ResponseBuilder tryResponseWith(RepositoryOperation operation, String message, HttpStatus status) {
        withOperation(operation);
        this.withMessage(message);
        this.withStatus(status);
        return this;
    }

    public ResponseEntity<Map<String,Object>> orElseWith(String message, HttpStatus status) {
        try {
            return this.buildWithOperation();
        } catch (Exception e) {
            return new ResponseBuilder()
                    .withStatus(status)
                    .withMessage(message)
                    .withError(e.getMessage())
                    .build();
        }
    }

    public ResponseEntity<Map<String,Object>> responseWithOperation(
            RepositoryOperation operation,
            String messageSuccess,
            HttpStatus statusSuccess,
            String messageError,
            HttpStatus statusError) {
        return new ResponseBuilder().tryResponseWith(
                operation,
                messageSuccess,
                statusSuccess
        ).orElseWith(
                messageError,
                statusError
        );
    }


    /*public ResponseEntity<Map<String,Object>> succes(Object data) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.OK)
                .withData(data)
                .withMessage("Operación realizada con éxito")
                .withInfo("Realizado")
                .build();
    }

    public ResponseEntity<Map<String,Object>> error(String message) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withMessage(message)
                .withInfo("Error")
                .build();
    }

    public ResponseEntity<Map<String,Object>> notFound(String message) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.NOT_FOUND)
                .withMessage(message)
                .withInfo("No encontrado")
                .build();
    }

    public ResponseEntity<Map<String,Object>> badRequest(String message) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withMessage(message)
                .withInfo("Petición incorrecta")
                .build();
    }

    public ResponseEntity<Map<String,Object>> unauthorized(String message) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.UNAUTHORIZED)
                .withMessage(message)
                .withInfo("No autorizado")
                .build();
    }

    public ResponseEntity<Map<String,Object>> forbidden(String message) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.FORBIDDEN)
                .withMessage(message)
                .withInfo("Prohibido")
                .build();
    }

    public ResponseEntity<Map<String,Object>> conflict(String message) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.CONFLICT)
                .withMessage(message)
                .withInfo("Conflicto")
                .build();
    }

    public ResponseEntity<Map<String,Object>> created(Object data) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.CREATED)
                .withData(data)
                .withMessage("Operación realizada con éxito")
                .withInfo("Creado")
                .build();
    }

    public ResponseEntity<Map<String,Object>> noContent() {
        return new ResponseBuilder()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Operación realizada con éxito")
                .withInfo("Sin contenido")
                .build();
    }

    public ResponseEntity<Map<String,Object>> ok(Object data) {
        return new ResponseBuilder()
                .withStatus(HttpStatus.OK)
                .withData(data)
                .withMessage("Operación realizada con éxito")
                .withInfo("Realizado")
                .build();
    }*/

}

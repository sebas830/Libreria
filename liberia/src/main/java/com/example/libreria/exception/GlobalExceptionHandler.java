package com.example.libreria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Estructura de error estandarizada
    static class ErrorResponse {
        private String message;
        private int status;
        private LocalDateTime timestamp;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
            this.timestamp = LocalDateTime.now();
        }

        public String getMessage() { return message; }
        public int getStatus() { return status; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    // 📌 Manejo de "no encontrado" (ej: libro o usuario inexistente)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    // 📌 Manejo de reglas de negocio (ej: libro no disponible)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    // 📌 Manejo genérico (errores no controlados)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}

package com.example.libreria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //  Clase para respuesta estándar de error
    public static class ErrorResponse {
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

    //  Usuario, libro o préstamo no encontrado (404)
    @ExceptionHandler({
            UserNotFoundException.class,
            BookNotFoundException.class,
            LoanNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    //  Regla de negocio inválida (ej. libro no disponible)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    // Cualquier otro error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ex.printStackTrace(); // opcional: registrar en logs
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}


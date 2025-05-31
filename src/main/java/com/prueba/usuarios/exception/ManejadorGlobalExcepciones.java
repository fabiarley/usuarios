package com.prueba.usuarios.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManejadorGlobalExcepciones extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<DetallesError> manejarRecursoNoEncontradoException(
            RecursoNoEncontradoException excepcion, WebRequest solicitudWeb) {

        var detallesError = new DetallesError(
                LocalDateTime.now(),
                excepcion.getMessage(),
                solicitudWeb.getDescription(false),
                "USUARIO_NO_ENCONTRADO"
        );

        return new ResponseEntity<>(detallesError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailYaExisteException.class)
    public ResponseEntity<DetallesError> manejarEmailYaExisteException(
            EmailYaExisteException excepcion, WebRequest solicitudWeb) {

        var detallesError = new DetallesError(
                LocalDateTime.now(),
                excepcion.getMessage(),
                solicitudWeb.getDescription(false),
                "EMAIL_YA_EXISTE"
        );

        return new ResponseEntity<>(detallesError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetallesError> manejarExcepcionGlobal(
            Exception excepcion, WebRequest solicitudWeb) {

        var detallesError = new DetallesError(
                LocalDateTime.now(),
                excepcion.getMessage(),
                solicitudWeb.getDescription(false),
                "ERROR_INTERNO_SERVIDOR"
        );

        return new ResponseEntity<>(detallesError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            errores.put(nombreCampo, mensajeError);
        });

        var detallesErrorValidacion = new RespuestaErrorValidacion(
                LocalDateTime.now(),
                "Falló la validación",
                request.getDescription(false),
                "ERROR_VALIDACION",
                errores
        );

        return new ResponseEntity<>(detallesErrorValidacion, HttpStatus.BAD_REQUEST);
    }
    // Clases de respuesta de error
    public record DetallesError(
            LocalDateTime timestamp,
            String mensaje,
            String ruta,
            String codigoError
    ) {}

    public record RespuestaErrorValidacion(
            LocalDateTime timestamp,
            String mensaje,
            String ruta,
            String codigoError,
            Map<String, String> errores
    ) {}
}
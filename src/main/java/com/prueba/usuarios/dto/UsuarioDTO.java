package com.prueba.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Datos completos de un usuario")
public record UsuarioDTO(
        @Schema(description = "ID único del usuario", example = "1")
        Integer id,

        @Schema(description = "Nombre del usuario", example = "Juan Pérez")
        @NotBlank(message = "Nombre es requerido")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
        String nombre,

        @Schema(description = "Correo electrónico del usuario", example = "juan.perez@ejemplo.com")
        @NotBlank(message = "Email es requerido")
        @Email(message = "El Email debe ser válido")
        String email,

        @Schema(description = "Contraseña del usuario", example = "password123")
        @NotBlank(message = "Contraseña es requerida")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
        String contrasena,

        @Schema(description = "Fecha de creación del usuario")
        LocalDateTime fechaCreacion,

        @Schema(description = "Fecha de última actualización del usuario")
        LocalDateTime fechaActualizacion
) {}
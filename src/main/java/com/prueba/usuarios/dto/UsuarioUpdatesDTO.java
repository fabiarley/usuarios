package com.prueba.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para actualización parcial de un usuario")
public record UsuarioUpdatesDTO(
        @Schema(description = "Nombre del usuario", example = "Juan Pérez Actualizado")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
        String nombre,

        @Schema(description = "Correo electrónico del usuario", example = "juan.actualizado@ejemplo.com")
        @Email(message = "El Email debe ser válido")
        String email,

        @Schema(description = "Contraseña del usuario", example = "nuevacontrasena123")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
        String contrasena
) {}
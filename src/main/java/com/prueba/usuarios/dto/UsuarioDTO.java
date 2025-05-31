package com.prueba.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UsuarioDTO(
        Integer id,

        @NotBlank(message = "Nombre es requerido")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
        String nombre,

        @NotBlank(message = "Email es requerido")
        @Email(message = "El Email debe ser válido")
        String email,

        @NotBlank(message = "Contraseña es requerida")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
        String contrasena,

        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
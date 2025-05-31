package com.prueba.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateDTO(
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
        String nombre,

        @Email(message = "El Email debe ser válido")
        String email,

        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
        String contrasena
) {}
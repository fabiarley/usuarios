package com.prueba.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String nombre;


    @NotBlank(message = "Email es requerido")
    @Email(message = "el Email debe ser valido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String contrasena;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCrear() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }



}

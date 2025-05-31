package com.prueba.usuarios.controller;

import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdatesDTO;
import com.prueba.usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Crea un nuevo usuario con la información proporcionada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
            @ApiResponse(responseCode = "409", description = "El email ya existe")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuarioDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar un usuario",
            description = "Elimina un usuario existente basado en su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar")
            @PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Retorna una lista de todos los usuarios registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener un usuario por ID",
            description = "Retorna un usuario específico basado en su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario a consultar")
            @PathVariable Integer id) {
        return new ResponseEntity<>(usuarioService.obtenerUsuarioPorId(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar un usuario completamente",
            description = "Actualiza todos los campos de un usuario existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "El email ya existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar")
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(id, usuarioDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar un usuario parcialmente",
            description = "Actualiza selectivamente los campos de un usuario existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "El email ya existe")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarParcialmenteUsuario(
            @Parameter(description = "ID del usuario a actualizar parcialmente")
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdatesDTO usuarioUpdateDTO) {
        return new ResponseEntity<>(usuarioService.actualizarParcialmenteUsuario(id, usuarioUpdateDTO), HttpStatus.OK);
    }
}
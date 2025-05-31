package com.prueba.usuarios.controller;

import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdateDTO;
import com.prueba.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuarioDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        return new ResponseEntity<>(usuarioService.obtenerUsuarioPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(id, usuarioDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarParcialmenteUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        return new ResponseEntity<>(usuarioService.actualizarParcialmenteUsuario(id, usuarioUpdateDTO), HttpStatus.OK);
    }

}

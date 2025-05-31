package com.prueba.usuarios.service;

import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdatesDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> obtenerTodosLosUsuarios();
    UsuarioDTO obtenerUsuarioPorId(Integer id);
    UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO);
    UsuarioDTO actualizarParcialmenteUsuario(Integer id, UsuarioUpdatesDTO usuarioUpdateDTO);
    void eliminarUsuario(Integer id);
}
package com.prueba.usuarios.service;

import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdateDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    List<UsuarioDTO> obtenerTodosLosUsuarios();
    UsuarioDTO obtenerUsuarioPorId(Integer id);
    UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO);
    UsuarioDTO actualizarParcialmenteUsuario(Integer id, UsuarioUpdateDTO usuarioUpdateDTO);
    void eliminarUsuario(Integer id);
}
package com.prueba.usuarios.service.impl;

import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdateDTO;
import com.prueba.usuarios.exception.EmailYaExisteException;
import com.prueba.usuarios.exception.RecursoNoEncontradoException;
import com.prueba.usuarios.model.Usuario;
import com.prueba.usuarios.repository.UsuarioRepository;
import com.prueba.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new EmailYaExisteException("El email ya existe: " + usuarioDTO.email());
        }

        // Convertir DTO a entidad
        Usuario usuario = mapearAEntidad(usuarioDTO);

        // Guardar usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Retornar DTO
        return mapearADTO(usuarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        return mapearADTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        // Verificar si el email está siendo cambiado y si ya existe
        if (!usuario.getEmail().equals(usuarioDTO.email()) &&
                usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new EmailYaExisteException("El email ya existe: " + usuarioDTO.email());
        }

        // Actualizar propiedades del usuario
        usuario.setNombre(usuarioDTO.nombre());
        usuario.setEmail(usuarioDTO.email());
        usuario.setContrasena(usuarioDTO.contrasena());

        // Guardar usuario actualizado
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return mapearADTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarParcialmenteUsuario(Integer id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        // Actualizar campos solo si no son nulos
        if (usuarioUpdateDTO.nombre() != null) {
            usuario.setNombre(usuarioUpdateDTO.nombre());
        }

        if (usuarioUpdateDTO.email() != null && !usuarioUpdateDTO.email().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(usuarioUpdateDTO.email())) {
                throw new EmailYaExisteException("El email ya existe: " + usuarioUpdateDTO.email());
            }
            usuario.setEmail(usuarioUpdateDTO.email());
        }

        if (usuarioUpdateDTO.contrasena() != null) {
            usuario.setContrasena(usuarioUpdateDTO.contrasena());
        }

        // Guardar usuario actualizado
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return mapearADTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    // Métodos auxiliares para mapear entre entidad y DTO
    private UsuarioDTO mapearADTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getContrasena(),
                usuario.getFechaCreacion(),
                usuario.getFechaActualizacion()
        );
    }

    private Usuario mapearAEntidad(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.nombre());
        usuario.setEmail(usuarioDTO.email());
        usuario.setContrasena(usuarioDTO.contrasena());
        return usuario;
    }
}
package com.prueba.usuarios.service;

import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdatesDTO;
import com.prueba.usuarios.exception.EmailYaExisteException;
import com.prueba.usuarios.exception.RecursoNoEncontradoException;
import com.prueba.usuarios.model.Usuario;
import com.prueba.usuarios.repository.UsuarioRepository;
import com.prueba.usuarios.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;
    private UsuarioUpdatesDTO usuarioUpdateDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime ahora = LocalDateTime.now();

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Usuario Prueba");
        usuario.setEmail("prueba@ejemplo.com");
        usuario.setContrasena("contrasena123");
        usuario.setFechaCreacion(ahora);
        usuario.setFechaActualizacion(ahora);

        usuarioDTO = new UsuarioDTO(
                1,
                "Usuario Prueba",
                "prueba@ejemplo.com",
                "contrasena123",
                ahora,
                ahora
        );

        usuarioUpdateDTO = new UsuarioUpdatesDTO(
                "Usuario Actualizado",
                "actualizado@ejemplo.com",
                "nuevacontrasena123"
        );
    }

    @Test
    @DisplayName("Test para crear usuario con éxito")
    void crearUsuario_CuandoUsuarioValido_RetornaUsuarioGuardado() {
        // given
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // when
        UsuarioDTO resultado = usuarioService.crearUsuario(usuarioDTO);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(usuario.getId());
        assertThat(resultado.nombre()).isEqualTo(usuario.getNombre());
        assertThat(resultado.email()).isEqualTo(usuario.getEmail());

        verify(usuarioRepository).existsByEmail(anyString());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test para crear usuario con email existente")
    void crearUsuario_CuandoEmailExiste_LanzaExcepcion() {
        // given
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        // when & then
        assertThrows(EmailYaExisteException.class, () -> {
            usuarioService.crearUsuario(usuarioDTO);
        });

        verify(usuarioRepository).existsByEmail(anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test para obtener todos los usuarios")
    void obtenerTodosLosUsuarios_RetornaListaDeUsuarios() {
        // given
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // when
        List<UsuarioDTO> resultado = usuarioService.obtenerTodosLosUsuarios();

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).id()).isEqualTo(usuario.getId());
        assertThat(resultado.get(0).nombre()).isEqualTo(usuario.getNombre());

        verify(usuarioRepository).findAll();
    }

    @Test
    @DisplayName("Test para obtener usuario por ID cuando existe")
    void obtenerUsuarioPorId_CuandoUsuarioExiste_RetornaUsuario() {
        // given
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));

        // when
        UsuarioDTO resultado = usuarioService.obtenerUsuarioPorId(1);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(usuario.getId());
        assertThat(resultado.nombre()).isEqualTo(usuario.getNombre());

        verify(usuarioRepository).findById(anyInt());
    }

    @Test
    @DisplayName("Test para obtener usuario por ID cuando no existe")
    void obtenerUsuarioPorId_CuandoUsuarioNoExiste_LanzaExcepcion() {
        // given
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when & then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.obtenerUsuarioPorId(1);
        });

        verify(usuarioRepository).findById(anyInt());
    }

    @Test
    @DisplayName("Test para actualizar usuario cuando existe")
    void actualizarUsuario_CuandoUsuarioExiste_RetornaUsuarioActualizado() {
        // given
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // when
        UsuarioDTO resultado = usuarioService.actualizarUsuario(1, usuarioDTO);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(usuario.getId());
        assertThat(resultado.nombre()).isEqualTo(usuario.getNombre());

        verify(usuarioRepository).findById(anyInt());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test para actualizar parcialmente un usuario cuando existe")
    void actualizarParcialmenteUsuario_CuandoUsuarioExiste_RetornaUsuarioActualizado() {
        // given
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // when
        UsuarioDTO resultado = usuarioService.actualizarParcialmenteUsuario(1, usuarioUpdateDTO);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(usuario.getId());

        verify(usuarioRepository).findById(anyInt());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Test para eliminar usuario cuando existe")
    void eliminarUsuario_CuandoUsuarioExiste_NoRetornaNada() {
        // given
        when(usuarioRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(anyInt());

        // when
        usuarioService.eliminarUsuario(1);

        // then
        verify(usuarioRepository).existsById(anyInt());
        verify(usuarioRepository).deleteById(anyInt());
    }

    @Test
    @DisplayName("Test para eliminar usuario cuando no existe")
    void eliminarUsuario_CuandoUsuarioNoExiste_LanzaExcepcion() {
        // given
        when(usuarioRepository.existsById(anyInt())).thenReturn(false);

        // when & then
        assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.eliminarUsuario(1);
        });

        verify(usuarioRepository).existsById(anyInt());
        verify(usuarioRepository, never()).deleteById(anyInt());
    }
}
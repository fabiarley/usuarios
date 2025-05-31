package com.prueba.usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.usuarios.dto.UsuarioDTO;
import com.prueba.usuarios.dto.UsuarioUpdateDTO;
import com.prueba.usuarios.exception.EmailYaExisteException;
import com.prueba.usuarios.exception.RecursoNoEncontradoException;
import com.prueba.usuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDTO usuarioDTO;
    private UsuarioUpdateDTO usuarioUpdateDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime ahora = LocalDateTime.now();

        usuarioDTO = new UsuarioDTO(
                1,
                "Usuario Prueba",
                "prueba@ejemplo.com",
                "contrasena123",
                ahora,
                ahora
        );

        usuarioUpdateDTO = new UsuarioUpdateDTO(
                "Usuario Actualizado",
                "actualizado@ejemplo.com",
                "nuevacontrasena123"
        );
    }

    @Test
    @DisplayName("Test para crear usuario con datos válidos")
    void crearUsuario_CuandoDatosValidos_RetornaCreado() throws Exception {
        // given
        when(usuarioService.crearUsuario(any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        // when & then
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(usuarioDTO.id())))
                .andExpect(jsonPath("$.nombre", is(usuarioDTO.nombre())))
                .andExpect(jsonPath("$.email", is(usuarioDTO.email())));

        verify(usuarioService).crearUsuario(any(UsuarioDTO.class));
    }

    @Test
    @DisplayName("Test para obtener todos los usuarios")
    void obtenerTodosLosUsuarios_RetornaListaDeUsuarios() throws Exception {
        // given
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(usuarioDTO));

        // when & then
        mockMvc.perform(get("/api/usuarios"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(usuarioDTO.id())))
                .andExpect(jsonPath("$[0].nombre", is(usuarioDTO.nombre())))
                .andExpect(jsonPath("$[0].email", is(usuarioDTO.email())));

        verify(usuarioService).obtenerTodosLosUsuarios();
    }

    @Test
    @DisplayName("Test para obtener usuario por ID cuando existe")
    void obtenerUsuarioPorId_CuandoUsuarioExiste_RetornaUsuario() throws Exception {
        // given
        when(usuarioService.obtenerUsuarioPorId(anyInt())).thenReturn(usuarioDTO);

        // when & then
        mockMvc.perform(get("/api/usuarios/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usuarioDTO.id())))
                .andExpect(jsonPath("$.nombre", is(usuarioDTO.nombre())))
                .andExpect(jsonPath("$.email", is(usuarioDTO.email())));

        verify(usuarioService).obtenerUsuarioPorId(anyInt());
    }

    @Test
    @DisplayName("Test para obtener usuario por ID cuando no existe")
    void obtenerUsuarioPorId_CuandoUsuarioNoExiste_RetornaNotFound() throws Exception {
        // given
        when(usuarioService.obtenerUsuarioPorId(anyInt()))
                .thenThrow(new RecursoNoEncontradoException("Usuario no encontrado con id: 1"));

        // when & then
        mockMvc.perform(get("/api/usuarios/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(usuarioService).obtenerUsuarioPorId(anyInt());
    }

    @Test
    @DisplayName("Test para actualizar usuario cuando existe")
    void actualizarUsuario_CuandoUsuarioExiste_RetornaUsuarioActualizado() throws Exception {
        // given
        when(usuarioService.actualizarUsuario(anyInt(), any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        // when & then
        mockMvc.perform(put("/api/usuarios/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(usuarioDTO.id())))
                .andExpect(jsonPath("$.nombre", is(usuarioDTO.nombre())))
                .andExpect(jsonPath("$.email", is(usuarioDTO.email())));

        verify(usuarioService).actualizarUsuario(anyInt(), any(UsuarioDTO.class));
    }
}

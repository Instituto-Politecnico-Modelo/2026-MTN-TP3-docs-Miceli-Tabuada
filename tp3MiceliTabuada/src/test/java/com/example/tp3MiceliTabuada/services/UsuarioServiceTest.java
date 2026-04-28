package com.example.tp3MiceliTabuada.services;

import com.example.tp3MiceliTabuada.models.Rol;
import com.example.tp3MiceliTabuada.models.Usuario;
import com.example.tp3MiceliTabuada.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setDni(12345678);
        usuario.setNombre("Francisco");
        usuario.setApellido("Miceli");
        usuario.setEmail("francisco@mail.com");
        usuario.setPassword("password123");
        usuario.setTelefono("1234567890");
        usuario.setRol(Rol.CLIENTE);
    }

    // --- ALTA ---
    @Test
    void crear_debeGuardarUsuario_cuandoDatosValidos() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByDni(usuario.getDni())).thenReturn(false);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.crear(usuario);

        assertNotNull(resultado);
        assertEquals("Francisco", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void crear_debeLanzarExcepcion_cuandoEmailDuplicado() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.crear(usuario));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void crear_debeLanzarExcepcion_cuandoDniDuplicado() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByDni(usuario.getDni())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.crear(usuario));
        verify(usuarioRepository, never()).save(any());
    }

    // --- CONSULTA ---
    @Test
    void obtenerTodos_debeRetornarListaDeUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.obtenerTodos();

        assertEquals(1, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_debeRetornarUsuario_cuandoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Francisco", resultado.get().getNombre());
    }

    @Test
    void obtenerPorId_debeRetornarVacio_cuandoNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    // --- MODIFICACIÓN ---
    @Test
    void modificar_debeActualizarUsuario_cuandoExiste() {
        Usuario datos = new Usuario();
        datos.setNombre("Juan");
        datos.setApellido("Tabuada");
        datos.setEmail("juan@mail.com");
        datos.setPassword("nuevaPass");
        datos.setTelefono("0987654321");
        datos.setRol(Rol.ADMIN);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Optional<Usuario> resultado = usuarioService.modificar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void modificar_debeRetornarVacio_cuandoNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.modificar(99L, usuario);

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, never()).save(any());
    }

    // --- BAJA ---
    @Test
    void eliminar_debeEliminarUsuario_cuandoExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean resultado = usuarioService.eliminar(1L);

        assertTrue(resultado);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminar_debeRetornarFalse_cuandoNoExiste() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        boolean resultado = usuarioService.eliminar(99L);

        assertFalse(resultado);
        verify(usuarioRepository, never()).deleteById(any());
    }
}


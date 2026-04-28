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
}

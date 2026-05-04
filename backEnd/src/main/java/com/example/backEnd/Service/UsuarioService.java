package com.example.backEnd.Service;

import com.example.backEnd.DTO.UsuarioRequestDTO;
import com.example.backEnd.DTO.UsuarioResponseDTO;
import com.example.backEnd.Entidad.Rol;
import com.example.backEnd.Entidad.Usuario;
import com.example.backEnd.Repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Alta
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email.");
        }
        if (usuarioRepository.existsByDni(dto.getDni())) {
            throw new RuntimeException("Ya existe un usuario con ese DNI.");
        }
        Rol rol = dto.getRol() != null ? dto.getRol() : Rol.CLIENTE;
        Usuario usuario = new Usuario(
                dto.getDni(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getTelefono(),
                rol
        );
        return toDTO(usuarioRepository.save(usuario));
    }

    // Obtener todos
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener por ID
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return toDTO(usuario);
    }

    // Modificación
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setTelefono(dto.getTelefono());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }
        return toDTO(usuarioRepository.save(usuario));
    }

    // Baja
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toDTO(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getDni(), u.getNombre(), u.getApellido(), u.getEmail(), u.getTelefono(), u.getRol());
    }
}

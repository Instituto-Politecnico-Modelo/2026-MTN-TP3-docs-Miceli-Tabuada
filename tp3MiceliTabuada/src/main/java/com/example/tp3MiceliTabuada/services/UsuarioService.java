package com.example.tp3MiceliTabuada.services;

import com.example.tp3MiceliTabuada.models.Usuario;
import com.example.tp3MiceliTabuada.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Alta
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.existsByDni(usuario.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese DNI");
        }
        // Hashear la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // Modificacion
    public Optional<Usuario> modificar(Long id, Usuario datos) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNombre(datos.getNombre());
            u.setApellido(datos.getApellido());
            u.setEmail(datos.getEmail());
            // Hashear la nueva contraseña antes de guardar
            u.setPassword(passwordEncoder.encode(datos.getPassword()));
            u.setTelefono(datos.getTelefono());
            u.setRol(datos.getRol());
            return usuarioRepository.save(u);
        });
    }

    // Baja
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


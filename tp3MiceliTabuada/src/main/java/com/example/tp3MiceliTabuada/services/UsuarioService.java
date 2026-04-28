package com.example.tp3MiceliTabuada.services;

import com.example.tp3MiceliTabuada.models.Usuario;
import com.example.tp3MiceliTabuada.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Alta
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.existsByDni(usuario.getDni())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese DNI");
        }
        return usuarioRepository.save(usuario);
    }
}


package com.example.tp3MiceliTabuada.repositories;

import com.example.tp3MiceliTabuada.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDni(Integer dni);
    boolean existsByEmail(String email);
    boolean existsByDni(Integer dni);
}


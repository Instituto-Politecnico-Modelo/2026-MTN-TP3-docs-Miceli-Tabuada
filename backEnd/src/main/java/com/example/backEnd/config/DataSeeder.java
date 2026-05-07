package com.example.backEnd.config;

import com.example.backEnd.Entidad.Rol;
import com.example.backEnd.Entidad.Usuario;
import com.example.backEnd.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminEmail = "admin@futbol5ya.com";
        if (!usuarioRepository.existsByEmail(adminEmail)) {
            Usuario admin = new Usuario(
                    0,
                    "Admin",
                    "Sistema",
                    adminEmail,
                    passwordEncoder.encode("Admin123!"),
                    "0000000000",
                    Rol.ADMINISTRADOR
            );
            usuarioRepository.save(admin);
            System.out.println(">> Admin por defecto creado: " + adminEmail);
        }
    }
}

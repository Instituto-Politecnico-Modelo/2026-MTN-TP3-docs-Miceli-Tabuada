package com.example.tp3MiceliTabuada.config;

import com.example.tp3MiceliTabuada.models.Rol;
import com.example.tp3MiceliTabuada.models.Usuario;
import com.example.tp3MiceliTabuada.repositories.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Solo crea el ADMIN si no existe ninguno con ese email
        if (usuarioRepository.findByEmail("admin@mail.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setDni(99999999);
            admin.setNombre("Admin");
            admin.setApellido("Root");
            admin.setEmail("admin@mail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setTelefono("1111111111");
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
            System.out.println(">>> Usuario ADMIN inicial creado: admin@mail.com / admin123");
        }
    }
}

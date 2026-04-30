package com.example.tp3MiceliTabuada.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.tp3MiceliTabuada.dto.AuthResponseDTO;
import com.example.tp3MiceliTabuada.dto.LoginRequestDTO;
import com.example.tp3MiceliTabuada.models.Rol;
import com.example.tp3MiceliTabuada.models.Usuario;
import com.example.tp3MiceliTabuada.security.CustomUserDetailsService;
import com.example.tp3MiceliTabuada.security.JwtUtil;
import com.example.tp3MiceliTabuada.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login y registro de usuarios")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    // POST http://localhost:8080/api/auth/login
    @Operation(summary = "Login", description = "Autentica un usuario y devuelve un token JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    // POST http://localhost:8080/api/auth/registro — público, crea siempre un CLIENTE
    @Operation(summary = "Registro de cliente", description = "Registra un nuevo usuario con rol CLIENTE. No requiere token.")
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        try {
            // Forzamos rol CLIENTE sin importar lo que mande el body
            usuario.setRol(Rol.CLIENTE);
            Usuario creado = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

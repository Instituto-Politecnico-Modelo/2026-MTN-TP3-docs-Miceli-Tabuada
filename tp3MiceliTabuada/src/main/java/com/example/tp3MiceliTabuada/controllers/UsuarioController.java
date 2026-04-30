package com.example.tp3MiceliTabuada.controllers;

import com.example.tp3MiceliTabuada.models.Usuario;
import com.example.tp3MiceliTabuada.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios (solo ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST http://localhost:8080/api/usuarios
    @Operation(summary = "Crear usuario", description = "Crea un usuario con cualquier rol. Solo ADMIN.")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        try {
            Usuario creado = usuarioService.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT http://localhost:8080/api/usuarios/{id}
    @Operation(summary = "Modificar usuario", description = "Modifica los datos de un usuario existente. Solo ADMIN.")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Usuario datos) {
        return usuarioService.modificar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE http://localhost:8080/api/usuarios/{id}
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID. Solo ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


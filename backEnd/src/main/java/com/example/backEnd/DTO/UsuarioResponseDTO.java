package com.example.backEnd.DTO;

import com.example.backEnd.Entidad.Rol;

public class UsuarioResponseDTO {
    private Long id;
    private Integer dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Rol rol;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(Long id, Integer dni, String nombre, String apellido, String email, String telefono, Rol rol) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public Integer getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
}

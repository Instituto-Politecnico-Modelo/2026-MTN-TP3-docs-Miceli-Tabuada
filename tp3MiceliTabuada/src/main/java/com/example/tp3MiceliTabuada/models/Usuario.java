package com.example.tp3MiceliTabuada.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios") //la tabla en la bdd
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremental en mysql
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String telefono;

    @Enumerated(EnumType.STRING) //guarda el nombre del rol en vez de un número
    @Column(nullable = false)
    private Rol rol;
}
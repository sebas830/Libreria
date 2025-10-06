package com.example.libreria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users") // Evita conflicto con palabra reservada en SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username; // nombre de usuario (login)

    @Column(nullable = false)
    private String password; // almacenada con BCrypt

    @Enumerated(EnumType.STRING) // Guarda el enum como texto: ROLE_USER o ROLE_LIBRARIAN
    @Column(nullable = false)
    private Role role;
}

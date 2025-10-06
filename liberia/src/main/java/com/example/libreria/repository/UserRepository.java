package com.example.libreria.repository;

import com.example.libreria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Busca un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
}

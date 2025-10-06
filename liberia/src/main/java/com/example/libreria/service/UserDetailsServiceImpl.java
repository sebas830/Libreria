package com.example.libreria.service;

import com.example.libreria.model.User;
import com.example.libreria.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Validación adicional: evitar NPE si el rol es null
        if (user.getRole() == null) {
            throw new UsernameNotFoundException("El usuario no tiene rol asignado");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                // Se pasa el nombre del enum directamente
                .roles(user.getRole().name().replace("ROLE_", ""))
                .build();
    }
}



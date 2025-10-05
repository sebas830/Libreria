package com.example.libreria.service;

import com.example.libreria.dto.UserCreateDTO;
import com.example.libreria.dto.UserDTO;
import com.example.libreria.exception.UserNotFoundException;
import com.example.libreria.model.User;
import com.example.libreria.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convertir entidad a DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword()); // 🔒 después puedes encriptar
        user.setRole(userCreateDTO.getRole());
        return convertToDTO(userRepository.save(user));
    }

    // 🔹 Actualizar usuario
    public UserDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));

        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword()); // 🔒 después puedes encriptar
        user.setRole(userCreateDTO.getRole());

        return convertToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

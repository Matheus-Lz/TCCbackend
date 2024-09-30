package com.pap.demo.services;

import com.pap.demo.DTOs.UserRequestDTO;
import com.pap.demo.DTOs.UserResponseDTO;
import com.pap.demo.model.User;
import com.pap.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Método para criar usuário
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Convertendo DTO de request para entidade User
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setCpf(userRequestDTO.getCpf());
        user.setPassword(userRequestDTO.getPassword());

        // Salvando o usuário no banco de dados
        User savedUser = userRepository.save(user);

        // Convertendo entidade User para DTO de response
        return toResponseDTO(savedUser);
    }

    // Método para atualizar usuário
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userRequestDTO.getName());
            user.setEmail(userRequestDTO.getEmail());
            user.setCpf(userRequestDTO.getCpf());
            user.setPassword(userRequestDTO.getPassword());
            User updatedUser = userRepository.save(user);
            return toResponseDTO(updatedUser);
        } else {
            return null;
        }
    }

    // Método para obter usuário por ID
    public UserResponseDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return toResponseDTO(userOptional.get());
        } else {
            return null;
        }
    }

    // Método auxiliar para converter User para UserResponseDTO
    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setIdUser(user.getIdUser());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCpf(user.getCpf());
        return userResponseDTO;
    }
}

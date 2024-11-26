package com.pap.demo.services;

import com.pap.demo.DTOs.UserRequestDTO;
import com.pap.demo.DTOs.UserResponseDTO;
import com.pap.demo.model.User;
import com.pap.demo.repositories.UserRepository;
import com.pap.demo.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    // Método para criar um novo usuário (registro)
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Verificar se o email já está cadastrado
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado: " + userRequestDTO.getEmail());
        }
        if (userRequestDTO.getCpf() == null && userRequestDTO.getCnpj() == null) {
            throw new RuntimeException("Erro: CPF ou CNPJ devem ser fornecidos.");
        }

        // Convertendo DTO de request para entidade User
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setCpf(userRequestDTO.getCpf());
        user.setCnpj(userRequestDTO.getCnpj());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        // Salvar o usuário no banco de dados
        User savedUser = userRepository.save(user);

        // Retornar o usuário criado sem gerar o token no cadastro
        return toResponseDTO(savedUser, null);
    }

    // Método para autenticar usuário (login)
    public String authenticateUser(UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userRequestDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword())) {
                UserDetails userDetails = loadUserByUsername(user.getEmail());
                return jwtUtil.generateToken(userDetails);
            } else {
                throw new BadCredentialsException("Senha incorreta");
            }
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + userRequestDTO.getEmail());
        }
    }

    // Método para carregar usuário pelo email (necessário para o filtro JWT)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles() // Não há mais roles associadas
                .build();
    }

    // Método para atualizar um usuário existente
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o id: " + id));

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setCpf(userRequestDTO.getCpf());
        user.setCnpj(userRequestDTO.getCnpj());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User updatedUser = userRepository.save(user);

        return toResponseDTO(updatedUser, null);
    }

    // Método para obter um usuário pelo ID
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o id: " + id));

        return toResponseDTO(user, null);
    }

    // Método auxiliar para converter User para UserResponseDTO
    private UserResponseDTO toResponseDTO(User user, String token) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setIdUser(user.getIdUser());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCpf(user.getCpf());
        userResponseDTO.setCnpj(user.getCnpj());
        userResponseDTO.setToken(token);
        return userResponseDTO;
    }
}

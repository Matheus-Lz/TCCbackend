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

        // Convertendo DTO de request para entidade User
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setCpf(userRequestDTO.getCpf());
        // Criptografar a senha antes de salvar no banco de dados
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        // Salvando o usuário no banco de dados
        User savedUser = userRepository.save(user);

        // Retornando o usuário criado sem gerar o token no cadastro
        return toResponseDTO(savedUser, null); // O token não é gerado no SignUp
    }
    // Método para autenticar usuário (login)
    public String authenticateUser(UserRequestDTO userRequestDTO) {
        // Procurar o usuário no banco pelo email
        Optional<User> userOptional = userRepository.findByEmail(userRequestDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Verificar se a senha fornecida corresponde à senha criptografada no banco
            if (passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword())) {
                // Gerar token JWT se as credenciais estiverem corretas
                UserDetails userDetails = loadUserByUsername(user.getEmail()); // Aqui você carrega o UserDetails

                return jwtUtil.generateToken(userDetails);  // Agora passando o objeto UserDetails corretamente
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
                .roles("USER") // Define o papel do usuário
                .build();
    }

    // Método para atualizar um usuário existente
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o id: " + id));

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setCpf(userRequestDTO.getCpf());
        // Criptografar a senha ao atualizar
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User updatedUser = userRepository.save(user);

        return toResponseDTO(updatedUser, null); // Não há necessidade de token na atualização
    }

    // Método para obter um usuário pelo ID
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o id: " + id));

        return toResponseDTO(user, null); // Não há necessidade de token na consulta
    }

    // Método auxiliar para converter User para UserResponseDTO
    private UserResponseDTO toResponseDTO(User user, String token) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setIdUser(user.getIdUser());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCpf(user.getCpf());
        userResponseDTO.setToken(token); // Incluindo o token no DTO de resposta, se houver
        return userResponseDTO;
    }
}

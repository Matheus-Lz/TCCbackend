package com.pap.demo.DTOs;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long idUser;
    private String name;
    private String email;
    private String cpf;
    private String cnpj; // Incluído para retornar o CNPJ se for um usuário administrador
    private String token; // Incluído apenas no login (sign in)
}


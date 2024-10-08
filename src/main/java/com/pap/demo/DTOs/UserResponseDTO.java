package com.pap.demo.DTOs;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long idUser;
    private String name;
    private String email;
    private String cpf;
}

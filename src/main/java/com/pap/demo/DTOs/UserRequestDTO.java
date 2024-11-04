package com.pap.demo.DTOs;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
    private String name;

    @Email(message = "Deve ser um email válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    // CPF agora é opcional, porque se o usuário tiver CNPJ, não precisa do CPF.
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 caracteres.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números.")
    private String cpf;

    // Novo campo CNPJ, também opcional.
    @Size(min = 14, max = 14, message = "O CNPJ deve conter 14 caracteres.")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter apenas números.")
    private String cnpj;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;
}


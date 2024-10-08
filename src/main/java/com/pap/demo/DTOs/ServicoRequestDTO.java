package com.pap.demo.DTOs;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ServicoRequestDTO {

    @NotBlank(message = "O nome do serviço é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome do serviço deve ter entre 2 e 100 caracteres.")
    private String name;

    @NotBlank(message = "A descrição do serviço é obrigatória.")
    @Size(min = 10, max = 255, message = "A descrição deve ter entre 10 e 255 caracteres.")
    private String description;

    @NotNull(message = "O preço é obrigatório.")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero.")
    private double price;

    @NotNull(message = "A duração em minutos é obrigatória.")
    @Min(value = 1, message = "A duração do serviço deve ser de pelo menos 1 minuto.")
    private int timeInMinutes;
}

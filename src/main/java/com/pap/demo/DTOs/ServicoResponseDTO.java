package com.pap.demo.DTOs;

import lombok.Data;

@Data
public class ServicoResponseDTO {

    private Long idServico;
    private String name;
    private String description;
    private double price;
    private int timeInMinutes;
}

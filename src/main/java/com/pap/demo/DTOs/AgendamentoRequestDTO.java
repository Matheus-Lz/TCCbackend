package com.pap.demo.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoRequestDTO {
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Long idServico;
    private Long idUsuario;
}

package com.pap.demo.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoResponseDTO {
    private Long id;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}

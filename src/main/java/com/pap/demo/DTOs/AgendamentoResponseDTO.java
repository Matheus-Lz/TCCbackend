package com.pap.demo.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoResponseDTO {
    private Long id;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String emailUsuario; // Email do usuário que realizou o agendamento
    private String cpfUsuario;
    private String nameServico; // CPF do usuário que realizou o agendamento
}

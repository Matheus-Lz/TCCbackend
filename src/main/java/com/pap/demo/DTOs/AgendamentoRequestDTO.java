package com.pap.demo.DTOs;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class AgendamentoRequestDTO {

    @NotNull(message = "A descrição não pode ser nula.")
    @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres.")
    private String descricao;

    @NotNull(message = "A data de início não pode ser nula.")
    @Future(message = "A data de início deve ser uma data futura.")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim não pode ser nula.")
    @Future(message = "A data de fim deve ser uma data futura.")
    private LocalDateTime dataFim;

    @NotNull(message = "O ID do serviço não pode ser nulo.")
    private Long idServico;
}

package com.pap.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agendamentos") // Nome da tabela no banco
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Long idAgendamento;

    @NotNull(message = "O CPF do usuário é obrigatório.")
    @Column(name = "cpf", nullable = false, length = 11) // Limite de 11 caracteres para CPF
    private String cpf;

    @NotNull(message = "A descrição do agendamento é obrigatória.")
    @Column(name = "descricao", nullable = false, length = 255) // Define um limite de tamanho
    private String descricao;

    @NotNull(message = "A data de início é obrigatória.")
    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim é obrigatória.")
    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @NotNull(message = "O serviço do pet é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico_pet", nullable = false)
    private ServicosPet servico;

    @NotNull(message = "O usuário associado ao agendamento é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;
}

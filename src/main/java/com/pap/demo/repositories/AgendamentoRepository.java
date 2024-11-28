package com.pap.demo.repositories;

import com.pap.demo.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    /**
     * Verifica se há conflitos de horário para um CPF de usuário.
     */
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.usuario.cpf = :cpf AND ((:dataInicio BETWEEN a.dataInicio AND a.dataFim) OR (:dataFim BETWEEN a.dataInicio AND a.dataFim))")
    boolean existeConflitoDeHorario(
            @Param("cpf") String cpf,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    /**
     * Busca os agendamentos pelo CPF do usuário.
     */
    List<Agendamento> findByUsuarioCpf(String cpf);

    /**
     * Busca os agendamentos entre o início e o fim do dia.
     */
    List<Agendamento> findByDataInicioBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}

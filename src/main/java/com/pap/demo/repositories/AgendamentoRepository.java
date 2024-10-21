package com.pap.demo.repositories;

import com.pap.demo.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // Métodos adicionais, se necessário
}

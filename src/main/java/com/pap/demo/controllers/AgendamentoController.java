package com.pap.demo.controllers;

import com.pap.demo.DTOs.AgendamentoRequestDTO;
import com.pap.demo.DTOs.AgendamentoResponseDTO;
import com.pap.demo.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    /**
     * Endpoint para listar agendamentos.
     * Retorna todos os agendamentos ou apenas os agendamentos de uma data específica, se fornecida.
     */
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentos(
            @RequestParam(value = "date", required = false) String date) {
        List<AgendamentoResponseDTO> agendamentos;

        if (date != null && !date.isEmpty()) {
            // Caso uma data seja fornecida
            agendamentos = agendamentoService.listarAgendamentosPorData(date);
        } else {
            // Caso nenhuma data seja fornecida
            agendamentos = agendamentoService.listarAgendamentos();
        }

        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Endpoint para criar um novo agendamento.
     */
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@RequestBody AgendamentoRequestDTO agendamentoRequestDTO) {
        // Recupera o email do usuário autenticado
        String emailUsuarioAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

        // Cria o agendamento vinculado ao usuário autenticado
        AgendamentoResponseDTO novoAgendamento = agendamentoService.criarAgendamento(agendamentoRequestDTO);
        return ResponseEntity.ok(novoAgendamento);
    }

    /**
     * Endpoint para listar agendamentos por CPF do usuário autenticado.
     */
    @GetMapping("/usuario/meus-agendamentos")
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentosDoUsuarioAutenticado() {
        // Recupera o email do usuário autenticado
        String emailUsuarioAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lista os agendamentos vinculados ao usuário autenticado
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentosPorEmail(emailUsuarioAutenticado);
        return ResponseEntity.ok(agendamentos);
    }

    /**
     * Endpoint para deletar um agendamento pelo ID.
     */
    @DeleteMapping("/{idAgendamento}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long idAgendamento) {
        // Chama o serviço para deletar o agendamento
        agendamentoService.deletarAgendamento(idAgendamento);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}

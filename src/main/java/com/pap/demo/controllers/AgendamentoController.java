package com.pap.demo.controllers;

import com.pap.demo.DTOs.AgendamentoRequestDTO;
import com.pap.demo.DTOs.AgendamentoResponseDTO;
import com.pap.demo.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    // Endpoint para listar os agendamentos
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarAgendamentos() {
        List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    // Endpoint para criar um agendamento
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criarAgendamento(@RequestBody AgendamentoRequestDTO agendamentoRequestDTO) {
        AgendamentoResponseDTO novoAgendamento = agendamentoService.criarAgendamento(agendamentoRequestDTO);
        return ResponseEntity.ok(novoAgendamento);
    }
}

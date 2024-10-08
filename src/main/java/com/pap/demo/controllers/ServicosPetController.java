package com.pap.demo.controllers;

import com.pap.demo.DTOs.ServicoRequestDTO;
import com.pap.demo.DTOs.ServicoResponseDTO;
import com.pap.demo.services.ServicosPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/servicos")
public class ServicosPetController {

    @Autowired
    private ServicosPetService servicosPetService;

    // Endpoint para criar um novo serviço
    @PostMapping
    public ResponseEntity<ServicoResponseDTO> create(@Valid @RequestBody ServicoRequestDTO servicoRequestDTO) {
        ServicoResponseDTO createdServico = servicosPetService.createServico(servicoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServico);
    }

    // Endpoint para atualizar um serviço existente
    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ServicoRequestDTO servicoRequestDTO) {
        ServicoResponseDTO updatedServico = servicosPetService.updateServico(id, servicoRequestDTO);
        if (updatedServico != null) {
            return ResponseEntity.ok(updatedServico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para obter um serviço pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> getById(@PathVariable Long id) {
        ServicoResponseDTO servicoResponseDTO = servicosPetService.getServicoById(id);
        if (servicoResponseDTO != null) {
            return ResponseEntity.ok(servicoResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

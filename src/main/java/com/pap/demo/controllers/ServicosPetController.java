package com.pap.demo.controllers;

import com.pap.demo.DTOs.ServicoRequestDTO;
import com.pap.demo.DTOs.ServicoResponseDTO;
import com.pap.demo.services.ServicosPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    // Novo endpoint para listar todos os serviços
    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> getAllServicos() {
        List<ServicoResponseDTO> servicos = servicosPetService.getAllServicos();
        return ResponseEntity.ok(servicos);
    }

    // Endpoint para excluir um serviço pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean isDeleted = servicosPetService.deleteServico(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se o serviço foi excluído com sucesso
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found se o ID não existir
        }
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        servicosPetService.deleteServico(id);
        return new ResponseEntity <>(HttpStatus.OK);
    }*/

}

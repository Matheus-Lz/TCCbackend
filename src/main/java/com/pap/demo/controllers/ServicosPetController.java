package com.pap.demo.controllers;

import com.pap.demo.model.ServicosPet;
import com.pap.demo.services.ServicosPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/servicos")
public class ServicosPetController {

    private final ServicosPetService servicosPetService;

    @Autowired
    public ServicosPetController(ServicosPetService servicosPetService) {
        this.servicosPetService = servicosPetService;
    }

    // Criar um novo serviço
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ServicosPet servicosPet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicosPetService.create(servicosPet));
    }

    // Atualizar um serviço existente
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ServicosPet servicosPet) {
        return ResponseEntity.ok(servicosPetService.update(servicosPet));
    }

    // Buscar um serviço pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<ServicosPet> servico = servicosPetService.findById(id);
        if (servico.isPresent()) {
            return ResponseEntity.ok(servico.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado");
        }
    }

    // Buscar um serviço pelo nome
    @GetMapping("/nome/{name}")
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        Optional<ServicosPet> servico = servicosPetService.findByName(name);
        if (servico.isPresent()) {
            return ResponseEntity.ok(servico.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço com nome " + name + " não encontrado");
        }
    }

    // Listar todos os serviços com paginação
    @GetMapping
    public ResponseEntity<Page<ServicosPet>> list(Pageable pageable) {
        return ResponseEntity.ok(servicosPetService.list(pageable));
    }

    // Deletar um serviço pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<ServicosPet> servico = servicosPetService.findById(id);
        if (servico.isPresent()) {
            servicosPetService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

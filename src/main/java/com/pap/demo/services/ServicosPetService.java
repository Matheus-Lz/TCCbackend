package com.pap.demo.services;

import com.pap.demo.model.ServicosPet;
import com.pap.demo.repositories.ServicosPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicosPetService {

    @Autowired
    private ServicosPetRepository servicosPetRepository;

    // Método para criar um novo serviço
    public ServicosPet create(ServicosPet servicosPet) {
        return servicosPetRepository.save(servicosPet);
    }

    // Método para atualizar um serviço existente
    public ServicosPet update(ServicosPet servicosPet) {
        return servicosPetRepository.save(servicosPet);
    }

    // Método para buscar um serviço pelo ID
    public Optional<ServicosPet> findById(Long id) {
        return servicosPetRepository.findById(id);
    }

    // Método para buscar um serviço pelo nome
    public Optional<ServicosPet> findByName(String name) {
        return servicosPetRepository.findByName(name);
    }

    // Método para listar todos os serviços com paginação
    public Page<ServicosPet> list(Pageable pageable) {
        return servicosPetRepository.findAll(pageable);
    }

    // Método para deletar um serviço pelo ID
    public void delete(Long id) {
        servicosPetRepository.deleteById(id);
    }
}

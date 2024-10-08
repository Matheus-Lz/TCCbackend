package com.pap.demo.services;

import com.pap.demo.DTOs.ServicoRequestDTO;
import com.pap.demo.DTOs.ServicoResponseDTO;
import com.pap.demo.model.ServicosPet;
import com.pap.demo.repositories.ServicosPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicosPetService {

    @Autowired
    private ServicosPetRepository servicosPetRepository;

    // Método para criar um serviço
    public ServicoResponseDTO createServico(ServicoRequestDTO servicoRequestDTO) {
        // Convertendo DTO de request para entidade ServicosPet
        ServicosPet servico = new ServicosPet();
        servico.setName(servicoRequestDTO.getName());
        servico.setDescription(servicoRequestDTO.getDescription());
        servico.setPrice(servicoRequestDTO.getPrice());
        servico.setTimeInMinutes(servicoRequestDTO.getTimeInMinutes());

        // Salvando o serviço no banco de dados
        ServicosPet savedServico = servicosPetRepository.save(servico);

        // Convertendo entidade ServicosPet para DTO de response
        return toResponseDTO(savedServico);
    }

    // Método para atualizar um serviço
    public ServicoResponseDTO updateServico(Long id, ServicoRequestDTO servicoRequestDTO) {
        Optional<ServicosPet> servicoOptional = servicosPetRepository.findById(id);
        if (servicoOptional.isPresent()) {
            ServicosPet servico = servicoOptional.get();
            servico.setName(servicoRequestDTO.getName());
            servico.setDescription(servicoRequestDTO.getDescription());
            servico.setPrice(servicoRequestDTO.getPrice());
            servico.setTimeInMinutes(servicoRequestDTO.getTimeInMinutes());
            ServicosPet updatedServico = servicosPetRepository.save(servico);
            return toResponseDTO(updatedServico);
        } else {
            return null;
        }
    }

    // Método para obter serviço por ID
    public ServicoResponseDTO getServicoById(Long id) {
        Optional<ServicosPet> servicoOptional = servicosPetRepository.findById(id);
        if (servicoOptional.isPresent()) {
            return toResponseDTO(servicoOptional.get());
        } else {
            return null;
        }
    }

    // Método auxiliar para converter ServicosPet para ServicoResponseDTO
    private ServicoResponseDTO toResponseDTO(ServicosPet servico) {
        ServicoResponseDTO servicoResponseDTO = new ServicoResponseDTO();
        servicoResponseDTO.setIdServico(servico.getIdServico());
        servicoResponseDTO.setName(servico.getName());
        servicoResponseDTO.setDescription(servico.getDescription());
        servicoResponseDTO.setPrice(servico.getPrice());
        servicoResponseDTO.setTimeInMinutes(servico.getTimeInMinutes());
        return servicoResponseDTO;
    }
}

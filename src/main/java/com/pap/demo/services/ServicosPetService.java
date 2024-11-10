package com.pap.demo.services;

import com.pap.demo.DTOs.ServicoRequestDTO;
import com.pap.demo.DTOs.ServicoResponseDTO;
import com.pap.demo.model.ServicosPet;
import com.pap.demo.repositories.ServicosPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicosPetService {

    @Autowired
    private ServicosPetRepository servicosPetRepository;

    // Método para criar um serviço
    public ServicoResponseDTO createServico(ServicoRequestDTO servicoRequestDTO) {
        ServicosPet servico = new ServicosPet();
        servico.setName(servicoRequestDTO.getName());
        servico.setDescription(servicoRequestDTO.getDescription());
        servico.setPrice(servicoRequestDTO.getPrice());
        servico.setTimeInMinutes(servicoRequestDTO.getTimeInMinutes());
        ServicosPet savedServico = servicosPetRepository.save(servico);
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
        return servicoOptional.map(this::toResponseDTO).orElse(null);
    }

    // Novo método para obter todos os serviços
    public List<ServicoResponseDTO> getAllServicos() {
        List<ServicosPet> servicos = servicosPetRepository.findAll();
        return servicos.stream().map(this::toResponseDTO).collect(Collectors.toList());
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

    // Método para Deletar o Serviço
    public boolean deleteServico(Long id) {
        Optional<ServicosPet> servicoOptional = servicosPetRepository.findById(id);
        if (servicoOptional.isPresent()) {
            servicosPetRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    /*public void deleteServico(Long id) {
        ServicosPet servicosPet = servicosPetRepository.findById(id).orElseThrow();
        servicosPetRepository.delete(servicosPet);
    }*/

}

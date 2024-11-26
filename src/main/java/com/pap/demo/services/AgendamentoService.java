package com.pap.demo.services;

import com.pap.demo.model.Agendamento;
import com.pap.demo.model.User;
import com.pap.demo.repositories.AgendamentoRepository;
import com.pap.demo.repositories.UserRepository;
import com.pap.demo.DTOs.AgendamentoRequestDTO;
import com.pap.demo.DTOs.AgendamentoResponseDTO;
import com.pap.demo.exceptions.ConflictException;
import com.pap.demo.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista todos os agendamentos.
     */
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        return agendamentos.stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo agendamento.
     */
    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO agendamentoRequestDTO) {
        // Buscar o usuário pelo email
        User usuario = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado."));

        // Validar conflitos de horário
        boolean existeConflito = agendamentoRepository.existeConflitoDeHorario(
                usuario.getCpf(),
                agendamentoRequestDTO.getDataInicio(),
                agendamentoRequestDTO.getDataFim()
        );

        if (existeConflito) {
            throw new ConflictException("Conflito de horário detectado para o usuário.");
        }

        // Mapear o request DTO para a entidade Agendamento
        Agendamento agendamento = modelMapper.map(agendamentoRequestDTO, Agendamento.class);

        // Configurar os dados do usuário
        agendamento.setCpf(usuario.getCpf());
        agendamento.setUsuario(usuario);

        // Salvar a entidade Agendamento no banco de dados
        Agendamento novoAgendamento = agendamentoRepository.save(agendamento);

        // Retornar o DTO da resposta
        return modelMapper.map(novoAgendamento, AgendamentoResponseDTO.class);
    }

    /**
     * Lista os agendamentos vinculados ao email do usuário.
     */
    public List<AgendamentoResponseDTO> listarAgendamentosPorEmail(String email) {
        // Buscar o usuário pelo email
        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com o email fornecido não encontrado."));

        // Buscar os agendamentos do usuário
        List<Agendamento> agendamentos = agendamentoRepository.findByUsuarioCpf(usuario.getCpf());

        if (agendamentos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum agendamento encontrado para o usuário autenticado.");
        }

        return agendamentos.stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoResponseDTO.class))
                .collect(Collectors.toList());
    }
}

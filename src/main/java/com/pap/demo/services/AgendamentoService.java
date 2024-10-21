package com.pap.demo.services;

import com.pap.demo.model.Agendamento;
import com.pap.demo.repositories.AgendamentoRepository;
import com.pap.demo.DTOs.AgendamentoRequestDTO;
import com.pap.demo.DTOs.AgendamentoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AgendamentoResponseDTO> listarAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        // Correção aqui: O uso do `map` e `Collectors.toList()` foi ajustado
        return agendamentos.stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO agendamentoRequestDTO) {
        // Mapear o request DTO para a entidade Agendamento
        Agendamento agendamento = modelMapper.map(agendamentoRequestDTO, Agendamento.class);

        // Salvar a entidade Agendamento no banco de dados
        Agendamento novoAgendamento = agendamentoRepository.save(agendamento);

        // Retornar o DTO da resposta
        return modelMapper.map(novoAgendamento, AgendamentoResponseDTO.class);
    }
}

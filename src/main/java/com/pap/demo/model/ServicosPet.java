package com.pap.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "servicos")
public class ServicosPet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idServico;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int timeInMinutes; // Para indicar a duração do serviço

}

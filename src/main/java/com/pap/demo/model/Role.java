package com.pap.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data  // Lombok para gerar getters, setters e outros métodos automaticamente
@NoArgsConstructor // Adiciona um construtor sem argumentos
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true) // Garante que os nomes das roles sejam únicos
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>(); // Inicialização para evitar NullPointerException

    // Construtor para facilitar a criação de novas roles
    public Role(ERole name) {
        this.name = name;
    }
}


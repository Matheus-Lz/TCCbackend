package com.pap.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails { // Implementa UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;

    private String name;
    private String email;
    private String password;
    private String cpf;

    // Relacionamento Many-to-Many com Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(); // Um usuário pode ter vários papéis (roles)

    // Implementação dos métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Converte cada Role em SimpleGrantedAuthority
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password; // O campo password da sua model
    }

    @Override
    public String getUsername() {
        return this.email; // O Spring Security utiliza o campo 'username', aqui estamos usando o email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modifique conforme sua lógica, se desejar
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modifique conforme sua lógica, se desejar
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modifique conforme sua lógica, se desejar
    }

    @Override
    public boolean isEnabled() {
        return true; // Modifique conforme sua lógica, se desejar
    }
}

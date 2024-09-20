package com.pap.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Optional;
import com.pap.demo.model.User;
import com.pap.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    private final UserRepository repository;

    public User create(final User user) {
        Assert.isTrue(this.getByCpf(user.getCpf()).isEmpty(), "Já existe um usuário com este CPF cadastrado");
        final User newUser = repository.save(user);
        return newUser;
    }

    public User update(User user){
        Assert.notNull(user.getIdUser(), "Id deve ser informado");
        Assert.isTrue(this.getById(user.getIdUser()).isPresent(),"Usuário não identificado");
        return repository.save(user);
    }

    public Optional<User> getById(final Long id) { return repository.findById(id);}

    public Optional<User> getByCpf(final String cpf) { return repository.findByCpf(cpf); }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }
}

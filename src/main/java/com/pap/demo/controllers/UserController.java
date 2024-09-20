package com.pap.demo.controllers;

import com.pap.demo.model.User;
import com.pap.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/user")
public class UserController {

    @Autowired
    public UserController(final UserService service) {
        this.service = service;
    }

    private final UserService service;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(users));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody User users) {
        return ResponseEntity.ok(service.update(users));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Optional<User>> getByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.getByCpf(cpf));
    }

    @GetMapping
    public Page<User> list(Pageable pageable) {
        return service.list(pageable);
    }
}

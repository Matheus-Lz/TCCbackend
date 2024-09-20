package com.pap.demo.repositories;

import com.pap.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByCpf(final String cpf);
}

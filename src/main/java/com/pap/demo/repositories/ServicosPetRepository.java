package com.pap.demo.repositories;

import com.pap.demo.model.ServicosPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ServicosPetRepository extends JpaRepository <ServicosPet, Long>{
    Optional<ServicosPet> findByName(final String name);

}

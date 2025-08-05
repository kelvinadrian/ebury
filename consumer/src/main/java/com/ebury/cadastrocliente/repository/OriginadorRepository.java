package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.Originador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginadorRepository extends JpaRepository<Originador, Long> {
    
    Optional<Originador> findByDescricao(String descricao);
} 
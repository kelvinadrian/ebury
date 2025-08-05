package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {
    
    Optional<EstadoCivil> findByDescricao(String descricao);
} 
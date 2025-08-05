package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SexoRepository extends JpaRepository<Sexo, Long> {
    
    Optional<Sexo> findByCodigo(String codigo);
    
    Optional<Sexo> findByDescricao(String descricao);
} 
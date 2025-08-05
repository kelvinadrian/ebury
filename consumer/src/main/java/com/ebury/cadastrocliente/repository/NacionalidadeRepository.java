package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.Nacionalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, Long> {
    
    Optional<Nacionalidade> findByDescricao(String descricao);
    
    Optional<Nacionalidade> findByCodigo(String codigo);
} 
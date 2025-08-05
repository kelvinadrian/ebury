package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.TipoDePessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDePessoaRepository extends JpaRepository<TipoDePessoa, Long> {
    
    Optional<TipoDePessoa> findByCodigo(String codigo);
    
    Optional<TipoDePessoa> findByDescricao(String descricao);
} 
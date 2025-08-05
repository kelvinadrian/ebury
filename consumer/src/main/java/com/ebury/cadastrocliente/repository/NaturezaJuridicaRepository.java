package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.NaturezaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaturezaJuridicaRepository extends JpaRepository<NaturezaJuridica, Long> {
    
    Optional<NaturezaJuridica> findByCodigo(Integer codigo);
    
    Optional<NaturezaJuridica> findByDescricao(String descricao);
} 
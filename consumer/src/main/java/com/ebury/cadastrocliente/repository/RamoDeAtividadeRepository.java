package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.RamoDeAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RamoDeAtividadeRepository extends JpaRepository<RamoDeAtividade, Long> {
    
    Optional<RamoDeAtividade> findByDescricao(String descricao);
} 
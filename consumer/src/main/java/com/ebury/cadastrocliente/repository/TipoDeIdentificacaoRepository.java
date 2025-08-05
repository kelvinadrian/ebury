package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.TipoDeIdentificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDeIdentificacaoRepository extends JpaRepository<TipoDeIdentificacao, Long> {
    
    Optional<TipoDeIdentificacao> findByDescricao(String descricao);
} 
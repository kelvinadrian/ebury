package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.PorteDoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PorteDoClienteRepository extends JpaRepository<PorteDoCliente, Long> {
    
    Optional<PorteDoCliente> findByDescricao(String descricao);
} 
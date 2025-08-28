package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.Cliente;
import com.ebury.cadastrocliente.model.ClientePf;
import com.ebury.cadastrocliente.model.ClientePj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);
    
    boolean existsByCpfCnpj(String cpfCnpj);
    
    // Métodos específicos para PF e PJ
    Optional<ClientePf> findByCpf(String cpf);
    
    Optional<ClientePj> findByCnpj(String cnpj);
    
    boolean existsByCpf(String cpf);
    
    boolean existsByCnpj(String cnpj);
} 
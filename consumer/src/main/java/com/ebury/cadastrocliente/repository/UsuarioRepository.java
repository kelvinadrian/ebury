package com.ebury.cadastrocliente.repository;

import com.ebury.cadastrocliente.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByLogin(String login);
    
    Optional<Usuario> findByNome(String nome);
} 
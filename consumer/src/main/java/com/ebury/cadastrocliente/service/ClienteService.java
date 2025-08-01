package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.model.Cliente;
import com.ebury.cadastrocliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public Cliente processarECadastrarCliente(Cliente cliente) {
        log.info("Processando cadastro do cliente: {}", cliente.getNome());
        
        // TODO: Implementar validações e verificações de duplicidade
        // Por enquanto, apenas salvando o cliente
        
        // Salvar cliente
        Cliente clienteSalvo = clienteRepository.save(cliente);
        
        log.info("Cliente cadastrado com sucesso. ID: {}, Nome: {}, CPF/CNPJ: {}", 
                clienteSalvo.getId(), clienteSalvo.getNome(), clienteSalvo.getCpfCnpj());
        
        return clienteSalvo;
    }
} 
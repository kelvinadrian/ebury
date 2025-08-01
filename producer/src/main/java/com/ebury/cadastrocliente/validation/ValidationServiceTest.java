package com.ebury.cadastrocliente.validation;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste simples para demonstrar o funcionamento do sistema de validação.
 * Este teste mostra como o sistema valida automaticamente todos os campos.
 */
@SpringBootTest
public class ValidationServiceTest {
    
    @Autowired
    private ValidationService validationService;
    
    @Test
    public void testValidacaoCampoObrigatorio() {
        // Criar objeto com campo obrigatório vazio
        ClienteRequestDTO request = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        cliente.setNome(""); // Campo obrigatório vazio
        request.setCliente(cliente);
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        // Deve ter pelo menos um erro
        assertFalse(results.stream().allMatch(ValidationResult::isValid));
        
        // Deve ter erro específico para o campo nome
        boolean hasNomeError = results.stream()
                .anyMatch(result -> !result.isValid() && 
                        result.getFieldPath().contains("nome") && 
                        result.getMessage().contains("não pode estar vazio"));
        
        assertTrue(hasNomeError, "Deveria ter erro de validação para campo nome obrigatório");
    }
    
    @Test
    public void testValidacaoCPF() {
        // Criar objeto com CPF inválido
        ClienteRequestDTO request = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        cliente.setCpfCnpj("123"); // CPF inválido
        request.setCliente(cliente);
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        // Deve ter erro de CPF
        boolean hasCPFError = results.stream()
                .anyMatch(result -> !result.isValid() && 
                        result.getFieldPath().contains("cpfCnpj") && 
                        result.getMessage().contains("dígitos"));
        
        assertTrue(hasCPFError, "Deveria ter erro de validação para CPF inválido");
    }
    
    @Test
    public void testValidacaoData() {
        // Criar objeto com data inválida
        ClienteRequestDTO request = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        cliente.setDataDoCadastro("32122024"); // Data inválida (32/12/2024)
        request.setCliente(cliente);
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        // Deve ter erro de data
        boolean hasDateError = results.stream()
                .anyMatch(result -> !result.isValid() && 
                        result.getFieldPath().contains("dataDoCadastro") && 
                        result.getMessage().contains("Data inválida"));
        
        assertTrue(hasDateError, "Deveria ter erro de validação para data inválida");
    }
    
    @Test
    public void testValidacaoTelefone() {
        // Criar objeto com telefone inválido
        ClienteRequestDTO request = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        ClienteRequestDTO.ClientePfDTO clientePf = new ClienteRequestDTO.ClientePfDTO();
        clientePf.setTelefoneCelular(123); // Telefone muito curto
        cliente.setClientePf(clientePf);
        request.setCliente(cliente);
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        // Deve ter erro de telefone
        boolean hasPhoneError = results.stream()
                .anyMatch(result -> !result.isValid() && 
                        result.getFieldPath().contains("telefoneCelular") && 
                        result.getMessage().contains("10 ou 11 dígitos"));
        
        assertTrue(hasPhoneError, "Deveria ter erro de validação para telefone inválido");
    }
    
    @Test
    public void testValidacaoMultiplosErros() {
        // Criar objeto com múltiplos erros
        ClienteRequestDTO request = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        cliente.setNome(""); // Campo obrigatório vazio
        cliente.setCpfCnpj("123"); // CPF inválido
        cliente.setDataDoCadastro("32122024"); // Data inválida
        request.setCliente(cliente);
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        // Deve ter múltiplos erros
        long errorCount = results.stream().filter(result -> !result.isValid()).count();
        assertTrue(errorCount >= 3, "Deveria ter pelo menos 3 erros de validação");
        
        System.out.println("Erros encontrados:");
        results.stream()
                .filter(result -> !result.isValid())
                .forEach(result -> System.out.println("- " + result.getFieldPath() + ": " + result.getMessage()));
    }
    
    @Test
    public void testValidacaoSucesso() {
        // Criar objeto válido
        ClienteRequestDTO request = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO cliente = new ClienteRequestDTO.ClienteDTO();
        cliente.setNome("João Silva");
        cliente.setCpfCnpj("12345678901");
        cliente.setDataDoCadastro("15012024");
        cliente.setCodExterno("CLI001");
        request.setCliente(cliente);
        
        List<ValidationResult> results = validationService.validateObject(request);
        
        // Deve passar na validação
        assertTrue(results.stream().allMatch(ValidationResult::isValid), 
                "Objeto válido deveria passar em todas as validações");
    }
} 
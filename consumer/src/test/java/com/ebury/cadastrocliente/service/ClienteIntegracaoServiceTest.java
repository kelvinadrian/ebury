package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.ClientePf;
import com.ebury.cadastrocliente.model.ClientePj;
import com.ebury.cadastrocliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteIntegracaoServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteBuilderService clienteBuilderService;

    @InjectMocks
    private ClienteIntegracaoService clienteIntegracaoService;

    private ClienteRequestDTO dtoInclusao;
    private ClienteRequestDTO dtoAlteracao;
    private ClientePf clientePfIntegrado;
    private ClientePf clientePfExistente;

    @BeforeEach
    void setUp() {
        // Setup DTO para inclusão
        dtoInclusao = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO clienteData = new ClienteRequestDTO.ClienteDTO();
        clienteData.setNome("João Silva");
        clienteData.setCpfCnpj("12345678901");
        clienteData.setCliTipoDeManutencao("I");
        dtoInclusao.setCliente(clienteData);

        // Setup DTO para alteração
        dtoAlteracao = new ClienteRequestDTO();
        clienteData = new ClienteRequestDTO.ClienteDTO();
        clienteData.setNome("João Silva Atualizado");
        clienteData.setCpfCnpj("12345678901");
        clienteData.setCliTipoDeManutencao("A");
        dtoAlteracao.setCliente(clienteData);

        // Setup cliente PF integrado
        clientePfIntegrado = new ClientePf();
        clientePfIntegrado.setCpf("12345678901");
        clientePfIntegrado.setNome("João Silva Atualizado");

        // Setup cliente PF existente
        clientePfExistente = new ClientePf();
        clientePfExistente.setId(1L);
        clientePfExistente.setCpf("12345678901");
        clientePfExistente.setNome("João Silva");
    }

    @Test
    void testIncluirClientePf() {
        // Given
        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoInclusao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.empty());
        when(clienteRepository.save(any(ClientePf.class))).thenReturn(clientePfIntegrado);

        // When
        ClientePf resultado = clienteIntegracaoService.processarECadastrarCliente(dtoInclusao);

        // Then
        assertNotNull(resultado);
        assertEquals("12345678901", resultado.getCpf());
        assertEquals("João Silva Atualizado", resultado.getNome());
        assertFalse(resultado.getDesabilitado());
        assertFalse(resultado.getVerificadoCompliance());

        verify(clienteRepository).findByCpf("12345678901");
        verify(clienteRepository).save(any(ClientePf.class));
    }

    @Test
    void testAlterarClientePf() {
        // Given
        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoAlteracao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clientePfExistente));
        when(clienteRepository.save(any(ClientePf.class))).thenReturn(clientePfIntegrado);

        // When
        ClientePf resultado = clienteIntegracaoService.processarECadastrarCliente(dtoAlteracao);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId()); // ID original mantido
        assertEquals("12345678901", resultado.getCpf());
        assertEquals("João Silva Atualizado", resultado.getNome());
        assertFalse(resultado.getDesabilitado());
        assertFalse(resultado.getVerificadoCompliance());

        verify(clienteRepository).findByCpf("12345678901");
        verify(clienteRepository).save(any(ClientePf.class));
        
        // Verificar que o ID foi copiado para o objeto da integração
        verify(clienteRepository).save(argThat(cliente -> cliente.getId().equals(1L)));
    }

    @Test
    void testIncluirClientePfJaExistente() {
        // Given
        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoInclusao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clientePfExistente));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteIntegracaoService.processarECadastrarCliente(dtoInclusao);
        });

        assertEquals("Cliente com CPF 12345678901 já existe", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void testAlterarClientePfNaoExistente() {
        // Given
        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoAlteracao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteIntegracaoService.processarECadastrarCliente(dtoAlteracao);
        });

        assertEquals("Cliente PF com CPF 12345678901 não encontrado", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void testTipoManutencaoInvalido() {
        // Given
        ClienteRequestDTO dtoInvalido = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO clienteData = new ClienteRequestDTO.ClienteDTO();
        clienteData.setCpfCnpj("12345678901");
        clienteData.setCliTipoDeManutencao("X"); // Tipo inválido
        dtoInvalido.setCliente(clienteData);

        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoInvalido)).thenReturn(clientePfIntegrado);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteIntegracaoService.processarECadastrarCliente(dtoInvalido);
        });

        assertEquals("Tipo de manutenção inválido: X", exception.getMessage());
    }

    @Test
    void testDeterminarPessoaFisica() {
        // Teste para CPF (11 dígitos)
        ClienteRequestDTO dtoPf = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO clienteData = new ClienteRequestDTO.ClienteDTO();
        clienteData.setCpfCnpj("12345678901"); // CPF
        dtoPf.setCliente(clienteData);

        // Teste para CNPJ (14 dígitos)
        ClienteRequestDTO dtoPj = new ClienteRequestDTO();
        clienteData = new ClienteRequestDTO.ClienteDTO();
        clienteData.setCpfCnpj("12345678000195"); // CNPJ
        dtoPj.setCliente(clienteData);

        // Como o método é privado, testamos indiretamente através do processamento
        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoPf)).thenReturn(clientePfIntegrado);
        when(clienteBuilderService.criarClientePjAPartirDoContexto(dtoPj)).thenReturn(new ClientePj());
        when(clienteRepository.findByCpf(any())).thenReturn(Optional.empty());
        when(clienteRepository.findByCnpj(any())).thenReturn(Optional.empty());
        when(clienteRepository.save(any())).thenReturn(clientePfIntegrado);

        // Processar PF
        clienteIntegracaoService.processarECadastrarCliente(dtoPf);
        verify(clienteBuilderService).criarClientePfAPartirDoContexto(dtoPf);

        // Processar PJ
        clienteIntegracaoService.processarECadastrarCliente(dtoPj);
        verify(clienteBuilderService).criarClientePjAPartirDoContexto(dtoPj);
    }
}

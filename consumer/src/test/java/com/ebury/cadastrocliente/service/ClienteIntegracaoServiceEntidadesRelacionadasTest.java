package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.*;
import com.ebury.cadastrocliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteIntegracaoServiceEntidadesRelacionadasTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteBuilderService clienteBuilderService;

    @InjectMocks
    private ClienteIntegracaoService clienteIntegracaoService;

    private ClienteRequestDTO dtoAlteracao;
    private ClientePf clientePfIntegrado;
    private ClientePf clientePfExistente;

    @BeforeEach
    void setUp() {
        // Setup DTO para alteração
        dtoAlteracao = new ClienteRequestDTO();
        ClienteRequestDTO.ClienteDTO clienteData = new ClienteRequestDTO.ClienteDTO();
        clienteData.setNome("João Silva Atualizado");
        clienteData.setCpfCnpj("12345678901");
        clienteData.setCliTipoDeManutencao("A");
        dtoAlteracao.setCliente(clienteData);

        // Setup cliente PF integrado com entidades relacionadas
        clientePfIntegrado = new ClientePf();
        clientePfIntegrado.setCpf("12345678901");
        clientePfIntegrado.setNome("João Silva Atualizado");
        clientePfIntegrado.setRendamensal(new BigDecimal("5000.00"));
        clientePfIntegrado.setPatrimonio(new BigDecimal("100000.00"));
        
        // Adicionar endereço
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setMunicipio("São Paulo");
        endereco.setUf("SP");
        endereco.setCep("01234-567");
        clientePfIntegrado.setEnderecos(new ArrayList<>());
        clientePfIntegrado.getEnderecos().add(endereco);
        
        // Adicionar conta corrente
        ContaCorrente conta = new ContaCorrente();
        Agencia agencia = new Agencia();
        agencia.setId(1L);
        conta.setAgencia(agencia);
        conta.setNumero("12345-6");
        conta.setContaPreferencial(true);
        clientePfIntegrado.setContaCorrenteVOs(new ArrayList<>());
        clientePfIntegrado.getContaCorrenteVOs().add(conta);

        // Setup cliente PF existente
        clientePfExistente = new ClientePf();
        clientePfExistente.setId(1L);
        clientePfExistente.setCpf("12345678901");
        clientePfExistente.setNome("João Silva");
        clientePfExistente.setRendamensal(new BigDecimal("3000.00"));
        clientePfExistente.setPatrimonio(new BigDecimal("50000.00"));
        
        // Endereço existente (diferente)
        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setLogradouro("Av. Paulista");
        enderecoExistente.setNumero("1000");
        enderecoExistente.setBairro("Bela Vista");
        enderecoExistente.setMunicipio("São Paulo");
        enderecoExistente.setUf("SP");
        enderecoExistente.setCep("01310-100");
        clientePfExistente.setEnderecos(new ArrayList<>());
        clientePfExistente.getEnderecos().add(enderecoExistente);
        
        // Conta corrente existente (mesma agência e número)
        ContaCorrente contaExistente = new ContaCorrente();
        contaExistente.setAgencia(agencia);
        contaExistente.setNumero("12345-6");
        contaExistente.setContaPreferencial(false);
        clientePfExistente.setContaCorrenteVOs(new ArrayList<>());
        clientePfExistente.getContaCorrenteVOs().add(contaExistente);
    }

    @Test
    void testAlterarClientePfComEntidadesRelacionadas() {
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
        assertEquals(new BigDecimal("5000.00"), resultado.getRendamensal());
        assertEquals(new BigDecimal("100000.00"), resultado.getPatrimonio());
        
        // Verificar que o endereço foi atualizado
        assertNotNull(resultado.getEnderecos());
        assertEquals(1, resultado.getEnderecos().size());
        assertEquals("Rua das Flores", resultado.getEnderecos().get(0).getLogradouro());
        
        // Verificar que a conta corrente foi atualizada
        assertNotNull(resultado.getContaCorrenteVOs());
        assertEquals(1, resultado.getContaCorrenteVOs().size());
        assertTrue(resultado.getContaCorrenteVOs().get(0).getContaPreferencial());

        verify(clienteRepository).findByCpf("12345678901");
        verify(clienteRepository).save(any(ClientePf.class));
    }

    @Test
    void testAlterarClientePfComCapacidadeFinanceira() {
        // Given - Cliente com capacidade financeira existente
        CapacidadeFinanceira capacidadeExistente = new CapacidadeFinanceira();
        TipoDeCapacidade tipoRenda = new TipoDeCapacidade();
        tipoRenda.setTipo("RENDA_BRUTA_MENSAL");
        capacidadeExistente.setTipoDeCapacidade(tipoRenda);
        capacidadeExistente.setValor(new BigDecimal("3000.00"));
        clientePfExistente.setCapacidadeFinanceiraVOs(new ArrayList<>());
        clientePfExistente.getCapacidadeFinanceiraVOs().add(capacidadeExistente);

        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoAlteracao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clientePfExistente));
        when(clienteRepository.save(any(ClientePf.class))).thenReturn(clientePfIntegrado);

        // When
        ClientePf resultado = clienteIntegracaoService.processarECadastrarCliente(dtoAlteracao);

        // Then
        assertNotNull(resultado);
        assertEquals(new BigDecimal("5000.00"), resultado.getRendamensal());
        
        // Verificar que a capacidade financeira foi atualizada
        assertNotNull(resultado.getCapacidadeFinanceiraVOs());
        assertEquals(1, resultado.getCapacidadeFinanceiraVOs().size());
        assertEquals(new BigDecimal("5000.00"), resultado.getCapacidadeFinanceiraVOs().get(0).getValor());

        verify(clienteRepository).save(any(ClientePf.class));
    }

    @Test
    void testAlterarClientePfComOperacoesPermitidas() {
        // Given - Cliente com operações permitidas
        ClientePorModalidade operacaoExistente = new ClientePorModalidade();
        ModalidadeDeOperacao modalidade = new ModalidadeDeOperacao();
        modalidade.setTipo("COMPRA");
        operacaoExistente.setModalidadedeoperacao(modalidade);
        operacaoExistente.setTipoDeManutencao("I");
        clientePfExistente.setClientePorModalidadeVOs(new ArrayList<>());
        clientePfExistente.getClientePorModalidadeVOs().add(operacaoExistente);

        // Nova operação na integração
        ClientePorModalidade novaOperacao = new ClientePorModalidade();
        ModalidadeDeOperacao novaModalidade = new ModalidadeDeOperacao();
        novaModalidade.setTipo("VENDA");
        novaOperacao.setModalidadedeoperacao(novaModalidade);
        novaOperacao.setTipoDeManutencao("I");
        clientePfIntegrado.setClientePorModalidadeVOs(new ArrayList<>());
        clientePfIntegrado.getClientePorModalidadeVOs().add(novaOperacao);

        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoAlteracao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clientePfExistente));
        when(clienteRepository.save(any(ClientePf.class))).thenReturn(clientePfIntegrado);

        // When
        ClientePf resultado = clienteIntegracaoService.processarECadastrarCliente(dtoAlteracao);

        // Then
        assertNotNull(resultado);
        assertNotNull(resultado.getClientePorModalidadeVOs());
        assertEquals(1, resultado.getClientePorModalidadeVOs().size());
        assertEquals("VENDA", resultado.getClientePorModalidadeVOs().get(0).getModalidadedeoperacao().getTipo());

        verify(clienteRepository).save(any(ClientePf.class));
    }

    @Test
    void testAlterarClientePfComCorretoras() {
        // Given - Cliente com corretoras existentes
        CorretoraDoCliente corretoraExistente = new CorretoraDoCliente();
        corretoraExistente.setId(1L);
        corretoraExistente.setCnpjCorretora("12345678000199");
        clientePfExistente.setCorretoraDoClienteVOs(new ArrayList<>());
        clientePfExistente.getCorretoraDoClienteVOs().add(corretoraExistente);

        // Novas corretoras na integração
        CorretoraDoCliente novaCorretora = new CorretoraDoCliente();
        novaCorretora.setCnpjCorretora("98765432000188");
        clientePfIntegrado.setCorretoraDoClienteVOs(new ArrayList<>());
        clientePfIntegrado.getCorretoraDoClienteVOs().add(novaCorretora);

        when(clienteBuilderService.criarClientePfAPartirDoContexto(dtoAlteracao)).thenReturn(clientePfIntegrado);
        when(clienteRepository.findByCpf("12345678901")).thenReturn(Optional.of(clientePfExistente));
        when(clienteRepository.save(any(ClientePf.class))).thenReturn(clientePfIntegrado);

        // When
        ClientePf resultado = clienteIntegracaoService.processarECadastrarCliente(dtoAlteracao);

        // Then
        assertNotNull(resultado);
        assertNotNull(resultado.getCorretoraDoClienteVOs());
        assertEquals(1, resultado.getCorretoraDoClienteVOs().size());
        assertEquals("98765432000188", resultado.getCorretoraDoClienteVOs().get(0).getCnpjCorretora());

        verify(clienteRepository).save(any(ClientePf.class));
    }
}

package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteBuilderService {
    
    private final ClienteService clienteService;
    
    /**
     * Cria um cliente PF a partir do contexto (DTO)
     */
    public ClientePf criarClientePfAPartirDoContexto(ClienteRequestDTO entrada) {
        ClientePf clientePf = new ClientePf();
        
        if (entrada.getCliente() != null) {
            // Criar dados básicos do cliente
            criarClienteDadosBasicos(entrada, clientePf);
            
            // Preencher dados específicos de PF
            preencherDadosPf(entrada, clientePf);
            
            // Preencher dados relacionados
            preencherEnderecos(entrada, clientePf);
            preencherContaCorrente(entrada, clientePf);
            preencherEmailsDocumentos(entrada, clientePf);
            preencherOperacoesPermitidas(entrada, clientePf);
            preencherCorretoraQueRepresenta(entrada, clientePf);
            preencherCapacidadeFinanceira(entrada, clientePf);
            preencherRepresentanteLegal(entrada, clientePf);
        }
        
        return clientePf;
    }
    
    /**
     * Cria um cliente PJ a partir do contexto (DTO)
     */
    public ClientePj criarClientePjAPartirDoContexto(ClienteRequestDTO entrada) {
        ClientePj clientePj = new ClientePj();
        
        if (entrada.getCliente() != null) {
            // Criar dados básicos do cliente
            criarClienteDadosBasicos(entrada, clientePj);
            
            // Preencher dados específicos de PJ
            preencherDadosPj(entrada, clientePj);
            
            // Preencher dados relacionados
            preencherEnderecos(entrada, clientePj);
            preencherContaCorrente(entrada, clientePj);
            preencherEmailsDocumentos(entrada, clientePj);
            preencherOperacoesPermitidas(entrada, clientePj);
            preencherCorretoraQueRepresenta(entrada, clientePj);
            preencherContatos(entrada, clientePj);
            preencherCapacidadeFinanceiraPj(entrada, clientePj);
            preencherSocioAcionista(entrada, clientePj);
            preencherRepresentanteLegal(entrada, clientePj);
        }
        
        return clientePj;
    }
    
    /**
     * Cria dados básicos comuns entre PF e PJ
     */
    private void criarClienteDadosBasicos(ClienteRequestDTO entrada, Cliente cliente) {
        ClienteRequestDTO.ClienteDTO clienteData = entrada.getCliente();
        
        cliente.setNome(clienteData.getNome());
        cliente.setCodigoExterno(clienteData.getCodExterno());
        cliente.setCodigoCorporativo(clienteData.getCodCorporativo());
        cliente.setDataDeCadastro(converterData(clienteData.getDataDoCadastro()));
        cliente.setDataDeDesativacao(converterData(clienteData.getDataDeDesativacao()));
        cliente.setDesabilitado("S".equalsIgnoreCase(clienteData.getDesabilitado()));
        cliente.setIndicadorDeUtilizacaoDeAssinaturaDigital("S".equalsIgnoreCase(clienteData.getUtilizaAssinaturaDigital()));
        cliente.setTipoDeResidencia(clienteData.getTipoDeResidencia());
        cliente.setIndicadorPep("S".equalsIgnoreCase(clienteData.getPep()));
        cliente.setIban(clienteData.getIban());
        
        // Definir tipo de pessoa
        if (cliente instanceof ClientePf) {
            cliente.setTipoDePessoa(clienteService.obterTipoDePessoa("PF"));
        } else {
            cliente.setTipoDePessoa(clienteService.obterTipoDePessoa("PJ"));
        }
        
        // Outros campos
        cliente.setComplementoDaNaturezaDoCliente(obterComplementoDaNatureza(clienteData.getComplementoDaNatureza()));
        cliente.setNaturezaJuridica(clienteService.obterNaturezaJuridica(clienteData.getNaturezaJuridicaN1()));
        cliente.setOriginador(clienteService.obterOriginador(clienteData.getOriginador()));
        cliente.setGerenteAnalistaOriginador(clienteService.obterUsuario(clienteData.getGerenteAnalistaOriginador()));
        cliente.setGestor(clienteService.obterUsuario(clienteData.getGerenteAnalista()));
        
        // Configurações padrão
        cliente.setEventual('N');
        cliente.setVerificadoCompliance(false);
        cliente.setStatus(Cliente.StatusCliente.ATIVO);
    }
    
    /**
     * Preenche dados específicos de Pessoa Física
     */
    private void preencherDadosPf(ClienteRequestDTO entrada, ClientePf clientePf) {
        ClienteRequestDTO.ClienteDTO clienteData = entrada.getCliente();
        
        if (clienteData.getClientePf() != null) {
            ClienteRequestDTO.ClientePfDTO clientePfData = clienteData.getClientePf();
            
            clientePf.setCpf(clienteData.getCpfCnpj());
            clientePf.setSexo(clienteService.obterSexo(clientePfData.getSexo()));
            clientePf.setEstadoCivil(clienteService.obterEstadoCivil(clientePfData.getEstadoCivil()));
            clientePf.setDataDeNascimento(converterData(clientePfData.getDataDeNascimento()));
            clientePf.setTipoDeIdentificacao(clienteService.obterTipoDeIdentificacao(clientePfData.getDescrDocumIdentifcacao()));
            clientePf.setIdentidade(clientePfData.getDocumIdentificacao());
            clientePf.setOrgaoEmissor(clientePfData.getEmissorDocumIdentificacao());
            clientePf.setUfDeEmissao(clientePfData.getUfEmissorDocumIdentificacao());
            clientePf.setDataDeEmissao(converterData(clientePfData.getDataDocumIdentificacao()));
            clientePf.setNomeDaMae(clientePfData.getNomeDaMae());
            clientePf.setNomeDoPai(clientePfData.getNomeDoPai());
            clientePf.setNacionalidade(clienteService.obterNacionalidade(clientePfData.getNacionalidade()));
            clientePf.setMunicipioDaNaturalidade(clientePfData.getMunicipioDaNaturalidade());
            clientePf.setEstadoDaNaturalidade(converterUF(clientePfData.getUfDaNaturalidade()));
            clientePf.setNomedoconjuge(clientePfData.getNomeDoConjuge());
            clientePf.setTelefoneResidencial(clientePfData.getTelefoneResidencial() != null ? 
                clientePfData.getTelefoneResidencial().toString() : null);
            clientePf.setTelefoneComercial(clientePfData.getTelefoneComercial() != null ? 
                clientePfData.getTelefoneComercial().toString() : null);
            clientePf.setTelefoneCelular(clientePfData.getTelefoneCelular() != null ? 
                clientePfData.getTelefoneCelular().toString() : null);
            clientePf.setRendamensal(clientePfData.getRendaMensal() != null ? 
                new BigDecimal(clientePfData.getRendaMensal()) : BigDecimal.ZERO);
            clientePf.setPatrimonio(clientePfData.getPatrimonio() != null ? 
                new BigDecimal(clientePfData.getPatrimonio()) : BigDecimal.ZERO);
        }
    }
    
    /**
     * Preenche dados específicos de Pessoa Jurídica
     */
    private void preencherDadosPj(ClienteRequestDTO entrada, ClientePj clientePj) {
        ClienteRequestDTO.ClienteDTO clienteData = entrada.getCliente();
        
        if (clienteData.getClientePj() != null) {
            ClienteRequestDTO.ClientePjDTO clientePjData = clienteData.getClientePj();
            
            clientePj.setCnpj(clienteData.getCpfCnpj());
            clientePj.setInscricaoEstadual(clientePjData.getInscricaoEstadual() != null ? 
                clientePjData.getInscricaoEstadual().toString() : null);
            clientePj.setEstadoDeEmissao(clientePjData.getUfEmissorInscricaoEstadual());
            clientePj.setPorteDoCliente(clienteService.obterPorteDoCliente(clientePjData.getPorte()));
            clientePj.setRamoDeAtividade(clienteService.obterRamoDeAtividade(clientePjData.getRamoDeAtividade()));
            clientePj.setFaturamentomediomensal(clientePjData.getFaturamentoMedioMensal() != null ? 
                new BigDecimal(clientePjData.getFaturamentoMedioMensal()) : null);
        }
    }
    
    /**
     * Preenche endereços do cliente
     */
    private void preencherEnderecos(ClienteRequestDTO entrada, Cliente cliente) {
        // TODO: Implementar preenchimento de endereços
        cliente.setEnderecos(new ArrayList<>());
    }
    
    /**
     * Preenche contas correntes do cliente
     */
    private void preencherContaCorrente(ClienteRequestDTO entrada, Cliente cliente) {
        // TODO: Implementar preenchimento de contas correntes
        cliente.setContaCorrenteVOs(new ArrayList<>());
    }
    
    /**
     * Preenche emails para documentos
     */
    private void preencherEmailsDocumentos(ClienteRequestDTO entrada, Cliente cliente) {
        // TODO: Implementar preenchimento de emails para documentos
        cliente.setClienteEmailsParaReceberDocumentoVOs(new ArrayList<>());
    }
    
    /**
     * Preenche operações permitidas
     */
    private void preencherOperacoesPermitidas(ClienteRequestDTO entrada, Cliente cliente) {
        // TODO: Implementar preenchimento de operações permitidas
        cliente.setClientePorModalidadeVOs(new ArrayList<>());
    }
    
    /**
     * Preenche corretoras que representam
     */
    private void preencherCorretoraQueRepresenta(ClienteRequestDTO entrada, Cliente cliente) {
        // TODO: Implementar preenchimento de corretoras
        cliente.setCorretoraDoClienteVOs(new ArrayList<>());
    }
    
    /**
     * Preenche capacidade financeira para PF
     */
    private void preencherCapacidadeFinanceira(ClienteRequestDTO entrada, ClientePf clientePf) {
        // TODO: Implementar preenchimento de capacidade financeira
        clientePf.setCapacidadeFinanceiraVOs(new ArrayList<>());
    }
    
    /**
     * Preenche capacidade financeira para PJ
     */
    private void preencherCapacidadeFinanceiraPj(ClienteRequestDTO entrada, ClientePj clientePj) {
        // TODO: Implementar preenchimento de capacidade financeira PJ
        clientePj.setCapacidadeFinanceiraVOs(new ArrayList<>());
    }
    
    /**
     * Preenche representantes legais
     */
    private void preencherRepresentanteLegal(ClienteRequestDTO entrada, Cliente cliente) {
        // TODO: Implementar preenchimento de representantes legais
        cliente.setRepresentantesDoClienteVOs(new ArrayList<>());
    }
    
    /**
     * Preenche contatos (específico para PJ)
     */
    private void preencherContatos(ClienteRequestDTO entrada, ClientePj clientePj) {
        // TODO: Implementar preenchimento de contatos
    }
    
    /**
     * Preenche sócios acionistas (específico para PJ)
     */
    private void preencherSocioAcionista(ClienteRequestDTO entrada, ClientePj clientePj) {
        // TODO: Implementar preenchimento de sócios acionistas
        clientePj.setSociosAcionistasDoClienteVOs(new ArrayList<>());
    }
    
    // Métodos auxiliares
    private Date converterData(String data) {
        if (data == null || data.trim().isEmpty()) return null;
        try {
            // Assumindo formato DDMMYYYY
            if (data.length() == 8) {
                int dia = Integer.parseInt(data.substring(0, 2));
                int mes = Integer.parseInt(data.substring(2, 4)) - 1; // Mês começa em 0
                int ano = Integer.parseInt(data.substring(4, 8));
                return new Date(ano - 1900, mes, dia);
            }
        } catch (Exception e) {
            log.warn("Erro ao converter data: {}", data, e);
        }
        return null;
    }
    
    private ClientePf.UFComOutroEnum converterUF(String uf) {
        if (uf == null) return null;
        try {
            return ClientePf.UFComOutroEnum.valueOf(uf.toUpperCase());
        } catch (Exception e) {
            return ClientePf.UFComOutroEnum.OUTRO;
        }
    }
    
    private ComplementoDaNaturezaDoCliente obterComplementoDaNatureza(Integer codigo) {
        if (codigo == null) return null;
        // TODO: Implementar conforme necessário
        return null;
    }
}

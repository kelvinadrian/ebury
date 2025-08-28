package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.*;
import com.ebury.cadastrocliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteIntegracaoService {
    
    private final ClienteRepository clienteRepository;
    private final ClienteBuilderService clienteBuilderService;
    
    /**
     * Processa e salva/atualiza cliente baseado no tipo de manutenção
     */
    public Cliente processarECadastrarCliente(ClienteRequestDTO dto) {
        log.info("Processando cadastro do cliente: {}", dto.getCliente() != null ? dto.getCliente().getNome() : "N/A");
        
        String tipoManutencao = dto.getCliente() != null ? dto.getCliente().getCliTipoDeManutencao() : "I";
        
        // Determinar se é PF ou PJ
        boolean isPessoaFisica = isPessoaFisica(dto);
        
        if (isPessoaFisica) {
            return processarClientePf(dto, tipoManutencao);
        } else {
            return processarClientePj(dto, tipoManutencao);
        }
    }
    
    /**
     * Determina se é pessoa física baseado no CPF/CNPJ
     */
    private boolean isPessoaFisica(ClienteRequestDTO dto) {
        if (dto.getCliente() == null || dto.getCliente().getCpfCnpj() == null) {
            return false;
        }
        
        String cpfCnpj = dto.getCliente().getCpfCnpj().replaceAll("[^0-9]", "");
        return cpfCnpj.length() <= 11; // CPF tem 11 dígitos, CNPJ tem 14
    }
    
    /**
     * Processa cliente pessoa física
     */
    private ClientePf processarClientePf(ClienteRequestDTO dto, String tipoManutencao) {
        ClientePf clienteIntegrado = clienteBuilderService.criarClientePfAPartirDoContexto(dto);
        
        switch (tipoManutencao) {
            case "I": // Inclusão
                return incluirClientePf(clienteIntegrado);
            case "A": // Alteração
                return alterarClientePf(clienteIntegrado);
            case "E": // Exclusão
                return excluirClientePf(clienteIntegrado);
            default:
                throw new IllegalArgumentException("Tipo de manutenção inválido: " + tipoManutencao);
        }
    }
    
    /**
     * Processa cliente pessoa jurídica
     */
    private ClientePj processarClientePj(ClienteRequestDTO dto, String tipoManutencao) {
        ClientePj clienteIntegrado = clienteBuilderService.criarClientePjAPartirDoContexto(dto);
        
        switch (tipoManutencao) {
            case "I": // Inclusão
                return incluirClientePj(clienteIntegrado);
            case "A": // Alteração
                return alterarClientePj(clienteIntegrado);
            case "E": // Exclusão
                return excluirClientePj(clienteIntegrado);
            default:
                throw new IllegalArgumentException("Tipo de manutenção inválido: " + tipoManutencao);
        }
    }
    
    /**
     * Inclui novo cliente PF
     */
    private ClientePf incluirClientePf(ClientePf clienteIntegrado) {
        // Verificar se já existe cliente com o mesmo CPF
        Optional<ClientePf> clienteExistente = clienteRepository.findByCpf(clienteIntegrado.getCpf());
        if (clienteExistente.isPresent()) {
            throw new RuntimeException("Cliente com CPF " + clienteIntegrado.getCpf() + " já existe");
        }
        
        // Configurações para inclusão
        clienteIntegrado.setVerificadoCompliance(false);
        clienteIntegrado.setDesabilitado(false);
        clienteIntegrado.setDataDeDesativacao(null);
        
        ClientePf clienteSalvo = clienteRepository.save(clienteIntegrado);
        log.info("Cliente PF incluído com sucesso. ID: {}, CPF: {}", clienteSalvo.getId(), clienteSalvo.getCpf());
        
        return clienteSalvo;
    }
    
    /**
     * Inclui novo cliente PJ
     */
    private ClientePj incluirClientePj(ClientePj clienteIntegrado) {
        // Verificar se já existe cliente com o mesmo CNPJ
        Optional<ClientePj> clienteExistente = clienteRepository.findByCnpj(clienteIntegrado.getCnpj());
        if (clienteExistente.isPresent()) {
            throw new RuntimeException("Cliente com CNPJ " + clienteIntegrado.getCnpj() + " já existe");
        }
        
        // Configurações para inclusão
        clienteIntegrado.setVerificadoCompliance(false);
        clienteIntegrado.setDesabilitado(false);
        clienteIntegrado.setDataDeDesativacao(null);
        
        ClientePj clienteSalvo = clienteRepository.save(clienteIntegrado);
        log.info("Cliente PJ incluído com sucesso. ID: {}, CNPJ: {}", clienteSalvo.getId(), clienteSalvo.getCnpj());
        
        return clienteSalvo;
    }
    
    /**
     * Altera cliente PF existente com gerenciamento de entidades relacionadas
     */
    private ClientePf alterarClientePf(ClientePf clienteIntegrado) {
        // Buscar cliente existente
        Optional<ClientePf> clienteExistenteOpt = clienteRepository.findByCpf(clienteIntegrado.getCpf());
        if (clienteExistenteOpt.isEmpty()) {
            throw new RuntimeException("Cliente PF com CPF " + clienteIntegrado.getCpf() + " não encontrado");
        }
        
        ClientePf clienteAtual = clienteExistenteOpt.get();
        
        // Gerenciar entidades relacionadas antes de copiar o ID
        gerenciarEntidadesRelacionadasPf(clienteAtual, clienteIntegrado);
        
        // Copiar o ID do cliente existente para o objeto da integração
        clienteIntegrado.setId(clienteAtual.getId());
        
        // Configurações após alteração
        clienteIntegrado.setVerificadoCompliance(false);
        clienteIntegrado.setDesabilitado(false);
        clienteIntegrado.setDataDeDesativacao(null);
        
        ClientePf clienteSalvo = clienteRepository.save(clienteIntegrado);
        log.info("Cliente PF alterado com sucesso. ID: {}, CPF: {}", clienteSalvo.getId(), clienteSalvo.getCpf());
        
        return clienteSalvo;
    }
    
    /**
     * Altera cliente PJ existente com gerenciamento de entidades relacionadas
     */
    private ClientePj alterarClientePj(ClientePj clienteIntegrado) {
        // Buscar cliente existente
        Optional<ClientePj> clienteExistenteOpt = clienteRepository.findByCnpj(clienteIntegrado.getCnpj());
        if (clienteExistenteOpt.isEmpty()) {
            throw new RuntimeException("Cliente PJ com CNPJ " + clienteIntegrado.getCnpj() + " não encontrado");
        }
        
        ClientePj clienteAtual = clienteExistenteOpt.get();
        
        // Gerenciar entidades relacionadas antes de copiar o ID
        gerenciarEntidadesRelacionadasPj(clienteAtual, clienteIntegrado);
        
        // Copiar o ID do cliente existente para o objeto da integração
        clienteIntegrado.setId(clienteAtual.getId());
        
        // Configurações após alteração
        clienteIntegrado.setVerificadoCompliance(false);
        clienteIntegrado.setDesabilitado(false);
        clienteIntegrado.setDataDeDesativacao(null);
        
        ClientePj clienteSalvo = clienteRepository.save(clienteIntegrado);
        log.info("Cliente PJ alterado com sucesso. ID: {}, CNPJ: {}", clienteSalvo.getId(), clienteSalvo.getCnpj());
        
        return clienteSalvo;
    }
    
    /**
     * Gerencia entidades relacionadas para cliente PF
     */
    private void gerenciarEntidadesRelacionadasPf(ClientePf clienteAtual, ClientePf clienteIntegrado) {
        log.info("Gerenciando entidades relacionadas para cliente PF: {}", clienteIntegrado.getCpf());
        
        // Gerenciar capacidade financeira
        gerenciarCapacidadeFinanceira(clienteAtual, clienteIntegrado);
        
        // Gerenciar endereços
        gerenciarEnderecos(clienteAtual, clienteIntegrado);
        
        // Gerenciar contas correntes
        gerenciarContasCorrentes(clienteAtual, clienteIntegrado);
        
        // Gerenciar operações permitidas
        gerenciarOperacoesPermitidas(clienteAtual, clienteIntegrado);
        
        // Gerenciar corretoras
        gerenciarCorretoras(clienteAtual, clienteIntegrado);
        
        // Gerenciar representantes legais
        gerenciarRepresentantesLegais(clienteAtual, clienteIntegrado);
        
        // Gerenciar emails para documentos
        gerenciarEmailsDocumentos(clienteAtual, clienteIntegrado);
    }
    
    /**
     * Gerencia entidades relacionadas para cliente PJ
     */
    private void gerenciarEntidadesRelacionadasPj(ClientePj clienteAtual, ClientePj clienteIntegrado) {
        log.info("Gerenciando entidades relacionadas para cliente PJ: {}", clienteIntegrado.getCnpj());
        
        // Gerenciar capacidade financeira
        gerenciarCapacidadeFinanceira(clienteAtual, clienteIntegrado);
        
        // Gerenciar endereços
        gerenciarEnderecos(clienteAtual, clienteIntegrado);
        
        // Gerenciar contas correntes
        gerenciarContasCorrentes(clienteAtual, clienteIntegrado);
        
        // Gerenciar operações permitidas
        gerenciarOperacoesPermitidas(clienteAtual, clienteIntegrado);
        
        // Gerenciar corretoras
        gerenciarCorretoras(clienteAtual, clienteIntegrado);
        
        // Gerenciar representantes legais
        gerenciarRepresentantesLegais(clienteAtual, clienteIntegrado);
        
        // Gerenciar sócios acionistas (específico PJ)
        gerenciarSociosAcionistas(clienteAtual, clienteIntegrado);
        
        // Gerenciar contatos (específico PJ)
        gerenciarContatos(clienteAtual, clienteIntegrado);
        
        // Gerenciar emails para documentos
        gerenciarEmailsDocumentos(clienteAtual, clienteIntegrado);
    }
    
    /**
     * Gerencia capacidade financeira seguindo a lógica do sistema legado
     */
    private void gerenciarCapacidadeFinanceira(Cliente clienteAtual, Cliente clienteIntegrado) {
        // Lógica baseada no alterarClientePorIntegracao do sistema legado
        
        // Para renda mensal
        if (clienteIntegrado.getRendamensal() != null && clienteIntegrado.getRendamensal().compareTo(BigDecimal.ZERO) > 0) {
            alterarCapacidadeFinanceiraPorTipo(clienteAtual, "RENDA_BRUTA_MENSAL", clienteIntegrado.getRendamensal());
        } else {
            removerCapacidadeFinanceiraPorTipo(clienteAtual, "RENDA_BRUTA_MENSAL");
        }
        
        // Para patrimônio
        if (clienteIntegrado.getPatrimonio() != null && clienteIntegrado.getPatrimonio().compareTo(BigDecimal.ZERO) > 0) {
            alterarCapacidadeFinanceiraPorTipo(clienteAtual, "PATRIMONIO", clienteIntegrado.getPatrimonio());
        } else {
            removerCapacidadeFinanceiraPorTipo(clienteAtual, "PATRIMONIO");
        }
    }
    
    /**
     * Altera capacidade financeira por tipo (baseado no sistema legado)
     */
    private void alterarCapacidadeFinanceiraPorTipo(Cliente cliente, String tipo, BigDecimal valor) {
        boolean alterado = false;
        
        if (cliente.getCapacidadeFinanceiraVOs() != null) {
            for (CapacidadeFinanceira capacidade : cliente.getCapacidadeFinanceiraVOs()) {
                if (tipo.equals(capacidade.getTipoDeCapacidade().getTipo())) {
                    capacidade.setValor(valor);
                    alterado = true;
                    log.info("Capacidade financeira {} atualizada para valor: {}", tipo, valor);
                    break;
                }
            }
        }
        
        if (!alterado) {
            // Criar nova capacidade financeira
            TipoDeCapacidade tipoDeCapacidade = obterTipoDeCapacidadePorTipo(tipo);
            if (tipoDeCapacidade != null) {
                CapacidadeFinanceira novaCapacidade = new CapacidadeFinanceira();
                novaCapacidade.setTipoDeCapacidade(tipoDeCapacidade);
                novaCapacidade.setCliente(cliente);
                novaCapacidade.setValor(valor);
                novaCapacidade.setQuantidade(0);
                
                if (cliente.getCapacidadeFinanceiraVOs() == null) {
                    cliente.setCapacidadeFinanceiraVOs(new ArrayList<>());
                }
                cliente.getCapacidadeFinanceiraVOs().add(novaCapacidade);
                log.info("Nova capacidade financeira {} criada com valor: {}", tipo, valor);
            }
        }
    }
    
    /**
     * Remove capacidade financeira por tipo
     */
    private void removerCapacidadeFinanceiraPorTipo(Cliente cliente, String tipo) {
        if (cliente.getCapacidadeFinanceiraVOs() != null) {
            Iterator<CapacidadeFinanceira> it = cliente.getCapacidadeFinanceiraVOs().iterator();
            while (it.hasNext()) {
                CapacidadeFinanceira capacidade = it.next();
                if (tipo.equals(capacidade.getTipoDeCapacidade().getTipo())) {
                    it.remove();
                    log.info("Capacidade financeira {} removida", tipo);
                }
            }
        }
    }
    
    /**
     * Gerencia endereços do cliente
     */
    private void gerenciarEnderecos(Cliente clienteAtual, Cliente clienteIntegrado) {
        if (clienteIntegrado.getEnderecos() != null && !clienteIntegrado.getEnderecos().isEmpty()) {
            // Lógica similar ao sistema legado: verificar se endereço existe e atualizar
            for (Endereco enderecoIntegrado : clienteIntegrado.getEnderecos()) {
                boolean existeEndereco = false;
                
                for (Endereco enderecoAtual : clienteAtual.getEnderecos()) {
                    if (enderecosIguais(enderecoAtual, enderecoIntegrado)) {
                        // Atualizar endereço existente
                        atualizarEndereco(enderecoAtual, enderecoIntegrado);
                        existeEndereco = true;
                        break;
                    }
                }
                
                if (!existeEndereco) {
                    // Adicionar novo endereço
                    enderecoIntegrado.setCliente(clienteAtual);
                    clienteAtual.getEnderecos().add(enderecoIntegrado);
                    log.info("Novo endereço adicionado para cliente");
                }
            }
        }
    }
    
    /**
     * Gerencia contas correntes do cliente
     */
    private void gerenciarContasCorrentes(Cliente clienteAtual, Cliente clienteIntegrado) {
        if (clienteIntegrado.getContaCorrenteVOs() != null && !clienteIntegrado.getContaCorrenteVOs().isEmpty()) {
            for (ContaCorrente contaIntegrada : clienteIntegrado.getContaCorrenteVOs()) {
                boolean existe = false;
                
                for (ContaCorrente contaAtual : clienteAtual.getContaCorrenteVOs()) {
                    if (contasCorrentesIguais(contaAtual, contaIntegrada)) {
                        // Atualizar conta existente
                        atualizarContaCorrente(contaAtual, contaIntegrada);
                        existe = true;
                        break;
                    }
                }
                
                if (!existe) {
                    // Adicionar nova conta
                    contaIntegrada.setCliente(clienteAtual);
                    if (contaIntegrada.getDesativado() == null) {
                        contaIntegrada.setDesativado(false);
                    }
                    if (contaIntegrada.getContaPreferencial() == null) {
                        contaIntegrada.setContaPreferencial(false);
                    }
                    clienteAtual.getContaCorrenteVOs().add(contaIntegrada);
                    log.info("Nova conta corrente adicionada para cliente");
                }
            }
        }
    }
    
    /**
     * Gerencia operações permitidas
     */
    private void gerenciarOperacoesPermitidas(Cliente clienteAtual, Cliente clienteIntegrado) {
        if (clienteIntegrado.getClientePorModalidadeVOs() != null && !clienteIntegrado.getClientePorModalidadeVOs().isEmpty()) {
            for (ClientePorModalidade operacaoIntegrada : clienteIntegrado.getClientePorModalidadeVOs()) {
                boolean existe = false;
                ClientePorModalidade operacaoParaRemover = null;
                
                for (ClientePorModalidade operacaoAtual : clienteAtual.getClientePorModalidadeVOs()) {
                    if (operacoesPermitidasIguais(operacaoAtual, operacaoIntegrada)) {
                        existe = true;
                        if ("E".equals(operacaoIntegrada.getTipoDeManutencao())) {
                            operacaoParaRemover = operacaoAtual;
                        }
                        break;
                    }
                }
                
                if ("E".equals(operacaoIntegrada.getTipoDeManutencao()) && existe) {
                    clienteAtual.getClientePorModalidadeVOs().remove(operacaoParaRemover);
                    log.info("Operação permitida removida");
                } else if ("I".equals(operacaoIntegrada.getTipoDeManutencao()) && !existe) {
                    operacaoIntegrada.setCliente(clienteAtual);
                    clienteAtual.getClientePorModalidadeVOs().add(operacaoIntegrada);
                    log.info("Nova operação permitida adicionada");
                }
            }
        }
    }
    
    /**
     * Gerencia corretoras
     */
    private void gerenciarCorretoras(Cliente clienteAtual, Cliente clienteIntegrado) {
        if (clienteIntegrado.getCorretoraDoClienteVOs() != null && !clienteIntegrado.getCorretoraDoClienteVOs().isEmpty()) {
            // Substituir completamente a lista de corretoras
            clienteAtual.getCorretoraDoClienteVOs().clear();
            for (CorretoraDoCliente corretora : clienteIntegrado.getCorretoraDoClienteVOs()) {
                corretora.setCliente(clienteAtual);
                clienteAtual.getCorretoraDoClienteVOs().add(corretora);
            }
            log.info("Lista de corretoras atualizada");
        }
    }
    
    /**
     * Gerencia representantes legais
     */
    private void gerenciarRepresentantesLegais(Cliente clienteAtual, Cliente clienteIntegrado) {
        if (clienteIntegrado.getRepresentantesDoClienteVOs() != null && !clienteIntegrado.getRepresentantesDoClienteVOs().isEmpty()) {
            clienteAtual.getRepresentantesDoClienteVOs().clear();
            for (RepresentantesDoCliente representante : clienteIntegrado.getRepresentantesDoClienteVOs()) {
                representante.setCliente(clienteAtual);
                clienteAtual.getRepresentantesDoClienteVOs().add(representante);
            }
            log.info("Lista de representantes legais atualizada");
        }
    }
    
    /**
     * Gerencia sócios acionistas (específico PJ)
     */
    private void gerenciarSociosAcionistas(ClientePj clienteAtual, ClientePj clienteIntegrado) {
        if (clienteIntegrado.getSociosAcionistasDoClienteVOs() != null && !clienteIntegrado.getSociosAcionistasDoClienteVOs().isEmpty()) {
            clienteAtual.getSociosAcionistasDoClienteVOs().clear();
            for (SociosAcionistasDoCliente socio : clienteIntegrado.getSociosAcionistasDoClienteVOs()) {
                socio.setCliente(clienteAtual);
                clienteAtual.getSociosAcionistasDoClienteVOs().add(socio);
            }
            log.info("Lista de sócios acionistas atualizada");
        }
    }
    
    /**
     * Gerencia contatos (específico PJ)
     */
    private void gerenciarContatos(ClientePj clienteAtual, ClientePj clienteIntegrado) {
        if (clienteIntegrado.getContatosDoClienteVOs() != null && !clienteIntegrado.getContatosDoClienteVOs().isEmpty()) {
            clienteAtual.getContatosDoClienteVOs().clear();
            for (ContatosDoCliente contato : clienteIntegrado.getContatosDoClienteVOs()) {
                contato.setCliente(clienteAtual);
                clienteAtual.getContatosDoClienteVOs().add(contato);
            }
            log.info("Lista de contatos atualizada");
        }
    }
    
    /**
     * Gerencia emails para documentos
     */
    private void gerenciarEmailsDocumentos(Cliente clienteAtual, Cliente clienteIntegrado) {
        if (clienteIntegrado.getClienteEmailsParaReceberDocumentoVOs() != null && !clienteIntegrado.getClienteEmailsParaReceberDocumentoVOs().isEmpty()) {
            clienteAtual.getClienteEmailsParaReceberDocumentoVOs().clear();
            for (ClienteEmailsParaReceberDocumento email : clienteIntegrado.getClienteEmailsParaReceberDocumentoVOs()) {
                email.setCliente(clienteAtual);
                clienteAtual.getClienteEmailsParaReceberDocumentoVOs().add(email);
            }
            log.info("Lista de emails para documentos atualizada");
        }
    }
    
    // Métodos auxiliares para comparação
    private boolean enderecosIguais(Endereco endereco1, Endereco endereco2) {
        return endereco1.getLogradouro().equalsIgnoreCase(endereco2.getLogradouro()) &&
               endereco1.getNumero().equalsIgnoreCase(endereco2.getNumero()) &&
               endereco1.getBairro().equalsIgnoreCase(endereco2.getBairro()) &&
               endereco1.getMunicipio().equalsIgnoreCase(endereco2.getMunicipio()) &&
               endereco1.getUf().equalsIgnoreCase(endereco2.getUf()) &&
               endereco1.getCep().equalsIgnoreCase(endereco2.getCep());
    }
    
    private boolean contasCorrentesIguais(ContaCorrente conta1, ContaCorrente conta2) {
        return conta1.getAgencia().getId().equals(conta2.getAgencia().getId()) &&
               conta1.getNumero().equals(conta2.getNumero());
    }
    
    private boolean operacoesPermitidasIguais(ClientePorModalidade op1, ClientePorModalidade op2) {
        return op1.getModalidadedeoperacao().getTipo().equals(op2.getModalidadedeoperacao().getTipo());
    }
    
    private void atualizarEndereco(Endereco enderecoAtual, Endereco enderecoIntegrado) {
        enderecoAtual.setLogradouro(enderecoIntegrado.getLogradouro());
        enderecoAtual.setNumero(enderecoIntegrado.getNumero());
        enderecoAtual.setBairro(enderecoIntegrado.getBairro());
        enderecoAtual.setMunicipio(enderecoIntegrado.getMunicipio());
        enderecoAtual.setUf(enderecoIntegrado.getUf());
        enderecoAtual.setCep(enderecoIntegrado.getCep());
        enderecoAtual.setComplemento(enderecoIntegrado.getComplemento());
        enderecoAtual.setPreferencial(enderecoIntegrado.getPreferencial());
    }
    
    private void atualizarContaCorrente(ContaCorrente contaAtual, ContaCorrente contaIntegrada) {
        if (contaIntegrada.getContaPreferencial() != null) {
            contaAtual.setContaPreferencial(contaIntegrada.getContaPreferencial());
        }
        if (contaIntegrada.getDesativado() != null) {
            contaAtual.setDesativado(contaIntegrada.getDesativado());
        }
    }
    
    private TipoDeCapacidade obterTipoDeCapacidadePorTipo(String tipo) {
        // TODO: Implementar busca no repositório
        // Por enquanto retorna null - deve ser implementado conforme necessário
        return null;
    }
    
    /**
     * Exclui cliente PF
     */
    private ClientePf excluirClientePf(ClientePf clienteIntegrado) {
        Optional<ClientePf> clienteExistenteOpt = clienteRepository.findByCpf(clienteIntegrado.getCpf());
        if (clienteExistenteOpt.isEmpty()) {
            throw new RuntimeException("Cliente PF com CPF " + clienteIntegrado.getCpf() + " não encontrado");
        }
        
        ClientePf clienteAtual = clienteExistenteOpt.get();
        clienteRepository.delete(clienteAtual);
        
        log.info("Cliente PF excluído com sucesso. ID: {}, CPF: {}", clienteAtual.getId(), clienteAtual.getCpf());
        
        return clienteAtual;
    }
    
    /**
     * Exclui cliente PJ
     */
    private ClientePj excluirClientePj(ClientePj clienteIntegrado) {
        Optional<ClientePj> clienteExistenteOpt = clienteRepository.findByCnpj(clienteIntegrado.getCnpj());
        if (clienteExistenteOpt.isEmpty()) {
            throw new RuntimeException("Cliente PJ com CNPJ " + clienteIntegrado.getCnpj() + " não encontrado");
        }
        
        ClientePj clienteAtual = clienteExistenteOpt.get();
        clienteRepository.delete(clienteAtual);
        
        log.info("Cliente PJ excluído com sucesso. ID: {}, CNPJ: {}", clienteAtual.getId(), clienteAtual.getCnpj());
        
        return clienteAtual;
    }
}

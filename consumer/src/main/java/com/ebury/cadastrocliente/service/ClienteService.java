package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.*;
import com.ebury.cadastrocliente.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final SexoRepository sexoRepository;
    private final EstadoCivilRepository estadoCivilRepository;
    private final NacionalidadeRepository nacionalidadeRepository;
    private final TipoDeIdentificacaoRepository tipoDeIdentificacaoRepository;
    private final PorteDoClienteRepository porteDoClienteRepository;
    private final RamoDeAtividadeRepository ramoDeAtividadeRepository;
    private final NaturezaJuridicaRepository naturezaJuridicaRepository;
    private final OriginadorRepository originadorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoDePessoaRepository tipoDePessoaRepository;
    private final ClienteIntegracaoService clienteIntegracaoService;
    
    public Cliente processarECadastrarCliente(ClienteRequestDTO dto) {
        log.info("Processando cadastro do cliente: {}", dto.getCliente() != null ? dto.getCliente().getNome() : "N/A");
        
        // Usar o serviço de integração para processar o cliente
        Cliente clienteSalvo = clienteIntegracaoService.processarECadastrarCliente(dto);
        
        log.info("Cliente processado com sucesso. ID: {}, Nome: {}, Tipo: {}", 
                clienteSalvo.getId(), clienteSalvo.getNome(), 
                clienteSalvo instanceof ClientePf ? "PF" : "PJ");
        
        return clienteSalvo;
    }
    
    public Cliente converterParaEntidade(ClienteRequestDTO dto) {
        Cliente cliente;
        
        // Dados básicos do cliente
        if (dto.getCliente() != null) {
            ClienteRequestDTO.ClienteDTO clienteData = dto.getCliente();
            
            // Determinar se é PF ou PJ baseado no CPF/CNPJ
            String cpfCnpj = clienteData.getCpfCnpj();
            boolean isPessoaFisica = cpfCnpj != null && cpfCnpj.length() <= 14;
            
            if (isPessoaFisica) {
                ClientePf clientePf = new ClientePf();
                cliente = clientePf;
                
                // Dados específicos de PF
                if (clienteData.getClientePf() != null) {
                    ClienteRequestDTO.ClientePfDTO clientePfData = clienteData.getClientePf();
                    clientePf.setCpf(cpfCnpj);
                    clientePf.setSexo(obterSexo(clientePfData.getSexo()));
                    clientePf.setDataDeNascimento(converterData(clientePfData.getDataDeNascimento()));
                    clientePf.setIdentidade(clientePfData.getDocumIdentificacao());
                    clientePf.setOrgaoEmissor(clientePfData.getEmissorDocumIdentificacao());
                    clientePf.setUfDeEmissao(clientePfData.getUfEmissorDocumIdentificacao());
                    clientePf.setDataDeEmissao(converterData(clientePfData.getDataDocumIdentificacao()));
                    clientePf.setNomeDaMae(clientePfData.getNomeDaMae());
                    clientePf.setNomeDoPai(clientePfData.getNomeDoPai());
                    clientePf.setMunicipioDaNaturalidade(clientePfData.getMunicipioDaNaturalidade());
                    clientePf.setEstadoDaNaturalidade(converterUF(clientePfData.getUfDaNaturalidade()));
                    clientePf.setTelefoneResidencial(clientePfData.getTelefoneResidencial() != null ? 
                        clientePfData.getTelefoneResidencial().toString() : null);
                    clientePf.setTelefoneComercial(clientePfData.getTelefoneComercial() != null ? 
                        clientePfData.getTelefoneComercial().toString() : null);
                    clientePf.setTelefoneCelular(clientePfData.getTelefoneCelular() != null ? 
                        clientePfData.getTelefoneCelular().toString() : null);
                    clientePf.setNomedoconjuge(clientePfData.getNomeDoConjuge());
                    clientePf.setNacionalidade(obterNacionalidade(clientePfData.getNacionalidade()));
                    clientePf.setRendamensal(clientePfData.getRendaMensal() != null ? 
                        new BigDecimal(clientePfData.getRendaMensal()) : null);
                    clientePf.setPatrimonio(clientePfData.getPatrimonio() != null ? 
                        new BigDecimal(clientePfData.getPatrimonio()) : null);
                    clientePf.setEstadoCivil(obterEstadoCivil(clientePfData.getEstadoCivil()));
                    clientePf.setTipoDeIdentificacao(obterTipoDeIdentificacao(clientePfData.getDescrDocumIdentifcacao()));
                }
                
            } else {
                ClientePj clientePj = new ClientePj();
                cliente = clientePj;
                
                // Dados específicos de PJ
                if (clienteData.getClientePj() != null) {
                    ClienteRequestDTO.ClientePjDTO clientePjData = clienteData.getClientePj();
                    clientePj.setCnpj(cpfCnpj);
                    clientePj.setInscricaoEstadual(clientePjData.getInscricaoEstadual() != null ? 
                        clientePjData.getInscricaoEstadual().toString() : null);
                    clientePj.setEstadoDeEmissao(clientePjData.getUfEmissorInscricaoEstadual());
                    clientePj.setPorteDoCliente(obterPorteDoCliente(clientePjData.getPorte()));
                    clientePj.setRazaoSocial(clienteData.getNome()); // Assumindo que o nome é a razão social
                    clientePj.setRamoDeAtividade(obterRamoDeAtividade(clientePjData.getRamoDeAtividade()));
                    clientePj.setFaturamentomediomensal(clientePjData.getFaturamentoMedioMensal() != null ? 
                        new BigDecimal(clientePjData.getFaturamentoMedioMensal()) : null);
                }
            }
            
            // Dados comuns
            cliente.setNome(clienteData.getNome());
            cliente.setCodigoExterno(clienteData.getCodExterno());
            cliente.setCodigoCorporativo(clienteData.getCodCorporativo());
            cliente.setDataDeCadastro(converterData(clienteData.getDataDoCadastro()));
            cliente.setDataDeDesativacao(converterData(clienteData.getDataDeDesativacao()));
            cliente.setDesabilitado("s".equalsIgnoreCase(clienteData.getDesabilitado()));
            cliente.setIndicadorDeUtilizacaoDeAssinaturaDigital("s".equalsIgnoreCase(clienteData.getUtilizaAssinaturaDigital()));
            cliente.setTipoDeResidencia(clienteData.getTipoDeResidencia());
            cliente.setIndicadorPep("s".equalsIgnoreCase(clienteData.getPep()));
            cliente.setIban(clienteData.getIban());
            cliente.setComplementoDaNaturezaDoCliente(obterComplementoDaNatureza(clienteData.getComplementoDaNatureza()));
            cliente.setNaturezaJuridica(obterNaturezaJuridica(clienteData.getNaturezaJuridicaN1()));
            cliente.setOriginador(obterOriginador(clienteData.getOriginador()));
            cliente.setGerenteAnalistaOriginador(obterUsuario(clienteData.getGerenteAnalistaOriginador()));
            cliente.setGerenteAnalista(obterUsuario(clienteData.getGerenteAnalista()));
            cliente.setTipoDePessoa(obterTipoDePessoa(clienteData.getTipoDePessoa()));
            
        } else {
            // Fallback para cliente básico
            cliente = new Cliente();
        }
        
        return cliente;
    }
    
    // Métodos para buscar entidades relacionadas
    public Sexo obterSexo(String codigo) {
        if (codigo == null) return null;
        
        // Primeiro tenta buscar por código
        Sexo sexo = sexoRepository.findByCodigo(codigo).orElse(null);
        if (sexo != null) return sexo;
        
        // Se não encontrar, busca por descrição ou cria novo
        String descricao = "M".equals(codigo) ? "Masculino" : "Feminino";
        sexo = sexoRepository.findByDescricao(descricao).orElse(null);
        if (sexo != null) return sexo;
        
        // Cria novo se não existir
        sexo = new Sexo();
        sexo.setCodigo(codigo);
        sexo.setDescricao(descricao);
        return sexoRepository.save(sexo);
    }
    
    public EstadoCivil obterEstadoCivil(String descricao) {
        if (descricao == null) return null;
        
        EstadoCivil estadoCivil = estadoCivilRepository.findByDescricao(descricao).orElse(null);
        if (estadoCivil != null) return estadoCivil;
        
        // Cria novo se não existir
        estadoCivil = new EstadoCivil();
        estadoCivil.setDescricao(descricao);
        return estadoCivilRepository.save(estadoCivil);
    }
    
    public Nacionalidade obterNacionalidade(String descricao) {
        if (descricao == null) return null;
        
        Nacionalidade nacionalidade = nacionalidadeRepository.findByDescricao(descricao).orElse(null);
        if (nacionalidade != null) return nacionalidade;
        
        // Cria novo se não existir
        nacionalidade = new Nacionalidade();
        nacionalidade.setDescricao(descricao);
        return nacionalidadeRepository.save(nacionalidade);
    }
    
    public TipoDeIdentificacao obterTipoDeIdentificacao(String descricao) {
        if (descricao == null) return null;
        
        TipoDeIdentificacao tipo = tipoDeIdentificacaoRepository.findByDescricao(descricao).orElse(null);
        if (tipo != null) return tipo;
        
        // Cria novo se não existir
        tipo = new TipoDeIdentificacao();
        tipo.setDescricao(descricao);
        return tipoDeIdentificacaoRepository.save(tipo);
    }
    
    public PorteDoCliente obterPorteDoCliente(String descricao) {
        if (descricao == null) return null;
        
        PorteDoCliente porte = porteDoClienteRepository.findByDescricao(descricao).orElse(null);
        if (porte != null) return porte;
        
        // Cria novo se não existir
        porte = new PorteDoCliente();
        porte.setDescricao(descricao);
        return porteDoClienteRepository.save(porte);
    }
    
    public RamoDeAtividade obterRamoDeAtividade(String descricao) {
        if (descricao == null) return null;
        
        RamoDeAtividade ramo = ramoDeAtividadeRepository.findByDescricao(descricao).orElse(null);
        if (ramo != null) return ramo;
        
        // Cria novo se não existir
        ramo = new RamoDeAtividade();
        ramo.setDescricao(descricao);
        return ramoDeAtividadeRepository.save(ramo);
    }
    
    public NaturezaJuridica obterNaturezaJuridica(Integer codigo) {
        if (codigo == null) return null;
        
        NaturezaJuridica natureza = naturezaJuridicaRepository.findByCodigo(codigo).orElse(null);
        if (natureza != null) return natureza;
        
        // Cria novo se não existir
        natureza = new NaturezaJuridica();
        natureza.setDescricao("Natureza " + codigo);
        return naturezaJuridicaRepository.save(natureza);
    }
    
    public Originador obterOriginador(String descricao) {
        if (descricao == null) return null;
        
        Originador originador = originadorRepository.findByDescricao(descricao).orElse(null);
        if (originador != null) return originador;
        
        // Cria novo se não existir
        originador = new Originador();
        originador.setDescricao(descricao);
        return originadorRepository.save(originador);
    }
    
    public Usuario obterUsuario(String login) {
        if (login == null) return null;
        
        Usuario usuario = usuarioRepository.findByLogin(login).orElse(null);
        if (usuario != null) return usuario;
        
        // Cria novo se não existir
        usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setNome(login);
        return usuarioRepository.save(usuario);
    }
    
    public TipoDePessoa obterTipoDePessoa(String codigo) {
        if (codigo == null) return null;
        
        TipoDePessoa tipo = tipoDePessoaRepository.findByCodigo(codigo).orElse(null);
        if (tipo != null) return tipo;
        
        // Cria novo se não existir
        tipo = new TipoDePessoa();
        tipo.setCodigo(codigo);
        tipo.setDescricao("Tipo " + codigo);
        return tipoDePessoaRepository.save(tipo);
    }
    
    public ComplementoDaNaturezaDoCliente obterComplementoDaNatureza(Integer codigo) {
        if (codigo == null) return null;
        // TODO: Implementar conforme necessário
        return null;
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
} 
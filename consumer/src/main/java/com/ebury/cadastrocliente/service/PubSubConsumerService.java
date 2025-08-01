package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PubSubConsumerService {
    
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;
    
    @Value("${spring.cloud.gcp.pubsub.subscriptions.cliente-cadastro:cliente-cadastro-sub}")
    private String clienteCadastroSubscription;
    
    @ServiceActivator(inputChannel = "clienteCadastroInputChannel")
    public void processarMensagemCliente(String payload, 
                                       @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        try {
            log.info("Processando mensagem recebida do Pub/Sub");
            
            // Parse da mensagem
            JsonNode jsonNode = objectMapper.readTree(payload);
            String messageId = jsonNode.get("messageId").asText();
            String tipoEvento = jsonNode.get("tipoEvento").asText();
            
            log.info("MessageId: {}, TipoEvento: {}", messageId, tipoEvento);
            
            if ("CLIENTE_CADASTRO".equals(tipoEvento)) {
                // Extrair dados do cliente
                JsonNode dadosNode = jsonNode.get("dados");
                ClienteRequestDTO clienteRequest = objectMapper.treeToValue(dadosNode, ClienteRequestDTO.class);
                
                // Converter para entidade
                Cliente cliente = converterParaEntidade(clienteRequest);
                
                // Processar e salvar
                Cliente clienteSalvo = clienteService.processarECadastrarCliente(cliente);
                
                log.info("Cliente processado com sucesso. ID: {}, MessageId: {}", 
                        clienteSalvo.getId(), messageId);
                
                // Acknowledge da mensagem
                message.ack();
                
            } else {
                log.warn("Tipo de evento não reconhecido: {}", tipoEvento);
                message.ack();
            }
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar JSON da mensagem", e);
            message.nack();
        } catch (Exception e) {
            log.error("Erro ao processar mensagem do cliente", e);
            message.nack();
        }
    }
    
    private Cliente converterParaEntidade(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        
        // Dados básicos do cliente
        if (dto.getCliente() != null) {
            ClienteRequestDTO.ClienteDTO clienteData = dto.getCliente();
            
            cliente.setCliTipoDeManutencao(clienteData.getCliTipoDeManutencao());
            cliente.setTipoDePessoa(clienteData.getTipoDePessoa());
            cliente.setCpfCnpj(clienteData.getCpfCnpj());
            cliente.setCodExterno(clienteData.getCodExterno());
            cliente.setCodCorporativo(clienteData.getCodCorporativo());
            cliente.setNome(clienteData.getNome());
            cliente.setDataDoCadastro(clienteData.getDataDoCadastro());
            cliente.setDataDeDesativacao(clienteData.getDataDeDesativacao());
            cliente.setDesabilitado(clienteData.getDesabilitado());
            cliente.setUtilizaAssinaturaDigital(clienteData.getUtilizaAssinaturaDigital());
            cliente.setNegociacao(clienteData.getNegociacao());
            cliente.setComplementoDaNatureza(clienteData.getComplementoDaNatureza());
            cliente.setNaturezaJuridicaN1(clienteData.getNaturezaJuridicaN1());
            cliente.setNaturezaJuridicaN2(clienteData.getNaturezaJuridicaN2());
            cliente.setOriginador(clienteData.getOriginador());
            cliente.setTipoDeResidencia(clienteData.getTipoDeResidencia());
            cliente.setGerenteAnalista(clienteData.getGerenteAnalista());
            cliente.setGerenteAnalistaOriginador(clienteData.getGerenteAnalistaOriginador());
            cliente.setPep(clienteData.getPep());
            cliente.setIban(clienteData.getIban());
            
            // Dados do cliente pessoa física
            if (clienteData.getClientePf() != null) {
                ClienteRequestDTO.ClientePfDTO clientePf = clienteData.getClientePf();
                cliente.setSexo(clientePf.getSexo());
                cliente.setEstadoCivil(clientePf.getEstadoCivil());
                cliente.setDataDeNascimento(clientePf.getDataDeNascimento());
                cliente.setDescrDocumIdentifcacao(clientePf.getDescrDocumIdentifcacao());
                cliente.setDocumIdentificacao(clientePf.getDocumIdentificacao());
                cliente.setEmissorDocumIdentificacao(clientePf.getEmissorDocumIdentificacao());
                cliente.setUfEmissorDocumIdentificacao(clientePf.getUfEmissorDocumIdentificacao());
                cliente.setDataDocumIdentificacao(clientePf.getDataDocumIdentificacao());
                cliente.setNomeDaMae(clientePf.getNomeDaMae());
                cliente.setNomeDoPai(clientePf.getNomeDoPai());
                cliente.setNacionalidade(clientePf.getNacionalidade());
                cliente.setMunicipioDaNaturalidade(clientePf.getMunicipioDaNaturalidade());
                cliente.setUfDaNaturalidade(clientePf.getUfDaNaturalidade());
                cliente.setNomeDoConjuge(clientePf.getNomeDoConjuge());
                cliente.setTelefoneResidencial(clientePf.getTelefoneResidencial());
                cliente.setTelefoneComercial(clientePf.getTelefoneComercial());
                cliente.setTelefoneCelular(clientePf.getTelefoneCelular());
                cliente.setRendaMensal(clientePf.getRendaMensal());
                cliente.setPatrimonio(clientePf.getPatrimonio());
            }
            
            // Dados do cliente pessoa jurídica
            if (clienteData.getClientePj() != null) {
                ClienteRequestDTO.ClientePjDTO clientePj = clienteData.getClientePj();
                cliente.setInscricaoEstadual(clientePj.getInscricaoEstadual());
                cliente.setUfEmissorInscricaoEstadual(clientePj.getUfEmissorInscricaoEstadual());
                cliente.setPorte(clientePj.getPorte());
                cliente.setRamoDeAtividade(clientePj.getRamoDeAtividade());
                cliente.setFaturamentoMedioMensal(clientePj.getFaturamentoMedioMensal());
            }
        }
        
        return cliente;
    }
} 
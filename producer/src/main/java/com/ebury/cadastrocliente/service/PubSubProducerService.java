package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PubSubProducerService {
    
    private final PubSubTemplate pubSubTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${spring.cloud.gcp.pubsub.topics.cliente-cadastro:cliente-cadastro}")
    private String clienteCadastroTopic;
    
    public Long enviarClienteParaProcessamento(ClienteRequestDTO clienteRequest) {
        try {
            String messageId = UUID.randomUUID().toString();
            
            Map<String, Object> evento = criarEventoCliente(messageId, clienteRequest);
            String mensagem = objectMapper.writeValueAsString(evento);
            
            pubSubTemplate.publish(clienteCadastroTopic, mensagem);
            log.info("Cliente enviado para processamento. MessageId: {}, Nome: {}", 
                    messageId, 
                    clienteRequest.getCliente() != null ? clienteRequest.getCliente().getNome() : "N/A");
            
            // TODO: Implementar geração real do ID do cliente
            // Por enquanto, retornando um ID simulado baseado no timestamp
            Long idDeClienteTree = System.currentTimeMillis();
            
            return idDeClienteTree;
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao serializar dados do cliente", e);
            throw new RuntimeException("Erro ao processar dados do cliente", e);
        } catch (Exception e) {
            log.error("Erro ao enviar cliente para processamento", e);
            throw new RuntimeException("Erro ao enviar cliente para processamento", e);
        }
    }
    
    private Map<String, Object> criarEventoCliente(String messageId, ClienteRequestDTO clienteRequest) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("messageId", messageId);
        evento.put("tipoEvento", "CLIENTE_CADASTRO");
        evento.put("timestamp", LocalDateTime.now().toString());
        evento.put("dados", clienteRequest);
        
        return evento;
    }
} 
package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import com.ebury.cadastrocliente.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PubSubConsumerService {
    
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;
    private final PubSubTemplate pubSubTemplate;
    
    @Value("${spring.cloud.gcp.pubsub.subscriptions.cliente-cadastro:cliente-cadastro-sub}")
    private String clienteCadastroSubscription;
    
    // Configurações de retry
    @Value("${app.pubsub.retry.max-attempts:3}")
    private int maxRetryAttempts;
    
    @Value("${app.pubsub.retry.delay-ms:1000}")
    private long retryDelayMs;
    
    @Value("${app.pubsub.retry.backoff-multiplier:2.0}")
    private double backoffMultiplier;
    
    // Configurações de dead letter queue
    @Value("${app.pubsub.dead-letter.enabled:false}")
    private boolean deadLetterEnabled;
    
    @Value("${app.pubsub.dead-letter.topic:cliente-cadastro-dlq}")
    private String deadLetterTopic;
    
    @ServiceActivator(inputChannel = "clienteCadastroInputChannel")
    public void processarMensagemCliente(String payload, 
                                       @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        
        // Extrair messageId para rastreamento
        String messageId = "unknown";
        int retryCount = 0;
        
        try {
            // Parse inicial para obter messageId
            JsonNode jsonNode = objectMapper.readTree(payload);
            messageId = jsonNode.get("messageId").asText();
            
            log.info("Iniciando processamento da mensagem. MessageId: {}, Tentativa: {}", messageId, retryCount + 1);
            
            // Processar com retry
            processarComRetry(payload, message, messageId, retryCount);
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar JSON da mensagem. MessageId: {}", messageId, e);
            message.nack();
        } catch (Exception e) {
            log.error("Erro inesperado ao processar mensagem. MessageId: {}", messageId, e);
            message.nack();
        }
    }
    
    private void processarComRetry(String payload, 
                                 BasicAcknowledgeablePubsubMessage message, 
                                 String messageId, 
                                 int currentRetry) {
        
        try {
            // Parse da mensagem
            JsonNode jsonNode = objectMapper.readTree(payload);
            String tipoEvento = jsonNode.get("tipoEvento").asText();
            
            log.info("Processando mensagem. MessageId: {}, TipoEvento: {}, Tentativa: {}", 
                    messageId, tipoEvento, currentRetry + 1);
            
            if ("CLIENTE_CADASTRO".equals(tipoEvento)) {
                // Extrair dados do cliente
                JsonNode dadosNode = jsonNode.get("dados");
                ClienteRequestDTO clienteRequest = objectMapper.treeToValue(dadosNode, ClienteRequestDTO.class);
                
                // Processar e salvar usando o ClienteService
                Cliente clienteSalvo = clienteService.processarECadastrarCliente(clienteRequest);
                
                log.info("Cliente processado com sucesso. ID: {}, MessageId: {}, Tentativa: {}", 
                        clienteSalvo.getId(), messageId, currentRetry + 1);
                
                // Acknowledge da mensagem
                message.ack();
                
            } else {
                log.warn("Tipo de evento não reconhecido: {}. MessageId: {}", tipoEvento, messageId);
                message.ack();
            }
            
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar JSON da mensagem. MessageId: {}, Tentativa: {}", messageId, currentRetry + 1, e);
            handleRetry(message, messageId, currentRetry, e);
        } catch (Exception e) {
            log.error("Erro ao processar mensagem do cliente. MessageId: {}, Tentativa: {}", messageId, currentRetry + 1, e);
            handleRetry(message, messageId, currentRetry, e);
        }
    }
    
    private void handleRetry(BasicAcknowledgeablePubsubMessage message, 
                           String messageId, 
                           int currentRetry, 
                           Exception error) {
        
        if (currentRetry < maxRetryAttempts) {
            // Calcular delay com backoff exponencial
            long delay = (long) (retryDelayMs * Math.pow(backoffMultiplier, currentRetry));
            
            log.warn("Tentativa {} falhou para MessageId: {}. Tentando novamente em {}ms. Erro: {}", 
                    currentRetry + 1, messageId, delay, error.getMessage());
            
            // Aguardar antes da próxima tentativa
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("Thread interrompida durante retry. MessageId: {}", messageId);
                message.nack();
                return;
            }
            
            // Fazer nova tentativa
            processarComRetry(message.getPubsubMessage().getData().toStringUtf8(), 
                            message, messageId, currentRetry + 1);
            
        } else {
            log.error("Número máximo de tentativas ({}) atingido para MessageId: {}. Desistindo do processamento.", 
                    maxRetryAttempts, messageId);
            
            // Enviar para dead letter queue se habilitado
            if (deadLetterEnabled) {
                enviarParaDeadLetterQueue(message, messageId, error);
            }
            
            // Fazer ACK para remover da fila principal
            message.ack();
        }
    }
    
    private void enviarParaDeadLetterQueue(BasicAcknowledgeablePubsubMessage message, 
                                         String messageId, 
                                         Exception error) {
        try {
            // Criar mensagem para dead letter queue
            Map<String, Object> deadLetterMessage = new HashMap<>();
            deadLetterMessage.put("originalMessageId", messageId);
            deadLetterMessage.put("originalPayload", message.getPubsubMessage().getData().toStringUtf8());
            deadLetterMessage.put("errorMessage", error.getMessage());
            deadLetterMessage.put("errorType", error.getClass().getSimpleName());
            deadLetterMessage.put("timestamp", LocalDateTime.now().toString());
            deadLetterMessage.put("retryAttempts", maxRetryAttempts);
            
            String deadLetterPayload = objectMapper.writeValueAsString(deadLetterMessage);
            
            // Enviar para dead letter queue
            pubSubTemplate.publish(deadLetterTopic, deadLetterPayload);
            
            log.info("Mensagem enviada para dead letter queue. MessageId: {}, Topic: {}", 
                    messageId, deadLetterTopic);
                    
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem para dead letter queue. MessageId: {}", messageId, e);
        }
    }
} 
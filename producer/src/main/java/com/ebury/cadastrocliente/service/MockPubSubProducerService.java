package com.ebury.cadastrocliente.service;

import com.ebury.cadastrocliente.dto.ClienteRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@ConditionalOnProperty(name = "spring.cloud.gcp.pubsub.enabled", havingValue = "false", matchIfMissing = false)
public class MockPubSubProducerService implements PubSubProducerService {

    @Override
    public Long enviarClienteParaProcessamento(ClienteRequestDTO clienteRequest) {
        // Simula o processamento sem enviar para o Google Cloud Pub/Sub
        Long mockId = Instant.now().toEpochMilli();
        
        log.info("🔧 MOCK MODE: Simulando envio para processamento");
        log.info("📋 Cliente: {}", clienteRequest.getCliente() != null ? 
                clienteRequest.getCliente().getNome() : "N/A");
        log.info("🆔 ID Gerado: {}", mockId);
        log.info("✅ Cliente processado com sucesso (MOCK)");
        
        return mockId;
    }
} 
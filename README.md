# Ebury Cadastro Cliente

Sistema de cadastro de clientes com arquitetura de microserviços usando Google Cloud Pub/Sub.

## Arquitetura

O sistema é composto por dois microserviços:

### 1. Producer (Porta 8080)
- **Função**: Recebe requisições de cadastro de clientes via REST API
- **Responsabilidade**: Valida dados e envia para fila de processamento
- **Tecnologias**: Spring Boot, Google Cloud Pub/Sub

### 2. Consumer (Porta 8081)
- **Função**: Processa mensagens da fila e salva no banco de dados
- **Responsabilidade**: Processamento assíncrono e persistência
- **Tecnologias**: Spring Boot, Google Cloud Pub/Sub, JPA/Hibernate

## Estrutura de Mensagens

### Producer → Consumer

```json
{
  "messageId": "uuid-único-da-mensagem",
  "tipoEvento": "CLIENTE_CADASTRO",
  "timestamp": "2024-01-01T10:00:00",
  "dados": {
    // Dados do cliente (ClienteRequestDTO)
  }
}
```

### Campos da Mensagem

- **messageId**: Identificador único da mensagem (UUID)
  - Usado para rastreabilidade
  - Permite deduplicação
  - Facilita debugging e monitoramento

- **tipoEvento**: Tipo da operação
  - Permite extensibilidade futura
  - Facilita roteamento de mensagens

- **timestamp**: Quando o evento foi criado
  - Útil para auditoria
  - Permite análise de latência

- **dados**: Payload da mensagem
  - Contém os dados do cliente
  - Estrutura flexível para diferentes tipos

## Sistema de Retry e Tratamento de Erros

### Configurações de Retry

```yaml
app:
  pubsub:
    retry:
      max-attempts: 3          # Máximo de tentativas
      delay-ms: 1000          # Delay inicial (1 segundo)
      backoff-multiplier: 2.0  # Multiplicador exponencial
    dead-letter:
      enabled: true
      topic: cliente-cadastro-dlq
      subscription: cliente-cadastro-dlq-sub
```

### Comportamento do Retry

1. **Tentativa 1**: Processamento imediato
2. **Tentativa 2**: Aguarda 1 segundo
3. **Tentativa 3**: Aguarda 2 segundos
4. **Após 3 falhas**: Envia para Dead Letter Queue

### Dead Letter Queue

Mensagens que falharam após todas as tentativas são enviadas para uma fila separada:

```json
{
  "originalMessageId": "uuid-da-mensagem-original",
  "originalPayload": "payload-original",
  "errorMessage": "Descrição do erro",
  "errorType": "NomeDaException",
  "timestamp": "2024-01-01T10:00:00",
  "retryAttempts": 3
}
```

## Processamento com Múltiplas Instâncias

### Como Funciona

1. **Distribuição Automática**: O Google Cloud Pub/Sub distribui mensagens automaticamente entre as instâncias
2. **Processamento Único**: Cada mensagem é entregue a apenas uma instância
3. **ACK/NACK**: Apenas a instância que recebeu a mensagem pode fazer ACK/NACK

### Exemplo com 3 Pods

```
Mensagem 1 → Pod A (processa e faz ACK)
Mensagem 2 → Pod B (processa e faz ACK)  
Mensagem 3 → Pod C (processa e faz ACK)
Mensagem 4 → Pod A (processa e faz ACK)
```

### Configurações de Concorrência

```yaml
spring:
  cloud:
    gcp:
      pubsub:
        subscriber:
          parallel-pull-count: 1      # Número de threads de pull
          max-concurrent-ack-count: 100  # Máximo de ACKs simultâneos
          ack-deadline: 30s           # Deadline para ACK
```

## Monitoramento e Observabilidade

### Logs Estruturados

```log
INFO  - Iniciando processamento da mensagem. MessageId: abc-123, Tentativa: 1
INFO  - Processando mensagem. MessageId: abc-123, TipoEvento: CLIENTE_CADASTRO, Tentativa: 1
INFO  - Cliente processado com sucesso. ID: 456, MessageId: abc-123, Tentativa: 1
WARN  - Tentativa 1 falhou para MessageId: abc-123. Tentando novamente em 1000ms. Erro: Database connection failed
ERROR - Número máximo de tentativas (3) atingido para MessageId: abc-123. Desistindo do processamento.
```

### Métricas Disponíveis

- `/actuator/health`: Status dos serviços
- `/actuator/metrics`: Métricas do sistema
- `/actuator/prometheus`: Métricas no formato Prometheus

## Deploy

### Kubernetes

```bash
# Producer
kubectl apply -f producer/k8s/

# Consumer  
kubectl apply -f consumer/k8s/
```

### Docker

```bash
# Producer
docker build -t producer .
docker run -p 8080:8080 producer

# Consumer
docker build -t consumer .
docker run -p 8081:8081 consumer
```

## Configurações de Ambiente

### Variáveis Obrigatórias

```bash
# Google Cloud
GCP_PROJECT_ID=seu-projeto-id
GCP_CREDENTIALS_LOCATION=/path/to/credentials.json

# Database
DB_HOST=localhost
DB_PORT=1433
DB_NAME=cadastro_cliente
DB_USERNAME=sa
DB_PASSWORD=password
```

## Troubleshooting

### Problemas Comuns

1. **Mensagens não processadas**
   - Verificar logs do consumer
   - Verificar configuração do Pub/Sub
   - Verificar conectividade com banco

2. **Erros de ACK**
   - Verificar deadline de ACK
   - Verificar timeout de processamento
   - Verificar recursos do sistema

3. **Dead Letter Queue**
   - Verificar se o topic DLQ existe
   - Verificar permissões de publicação
   - Analisar mensagens na DLQ

### Comandos Úteis

```bash
# Ver logs do consumer
kubectl logs -f deployment/consumer

# Ver métricas
curl http://localhost:8081/actuator/metrics

# Ver health check
curl http://localhost:8081/actuator/health
``` 
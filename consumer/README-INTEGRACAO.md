# Integração de Clientes - Consumer

Este documento explica a implementação da integração de clientes no microserviço consumer, baseada na lógica do sistema legado.

## Arquitetura

A implementação segue a mesma lógica do sistema legado, mas adaptada para o contexto de microserviços:

### 1. ClienteBuilderService
- **Responsabilidade**: Converte DTOs para entidades
- **Baseado em**: `ClienteAPartirDoContextoBuilder.java`
- **Funcionalidades**:
  - `criarClientePfAPartirDoContexto()` - Cria cliente PF
  - `criarClientePjAPartirDoContexto()` - Cria cliente PJ
  - Preenchimento de dados básicos e específicos
  - Conversão de tipos e formatação

### 2. ClienteIntegracaoService
- **Responsabilidade**: Gerencia operações de CRUD com lógica de integração
- **Baseado em**: `alterarClientePorIntegracao()` do `ClientePf.java`
- **Funcionalidades**:
  - Processamento por tipo de manutenção (I/A/E)
  - Preservação do ID original através de cópia
  - Validações de duplicidade
  - Substituição completa do objeto (mais eficiente)
  - **Gerenciamento inteligente de entidades relacionadas**

### 3. ClienteService
- **Responsabilidade**: Orquestra o processamento
- **Funcionalidades**:
  - Delega para `ClienteIntegracaoService`
  - Logging e monitoramento

## Fluxo de Processamento

### 1. Recebimento da Mensagem
```java
@ServiceActivator(inputChannel = "clienteCadastroInputChannel")
public void processarMensagemCliente(String payload, BasicAcknowledgeablePubsubMessage message)
```

### 2. Processamento Principal
```java
// ClienteService.processarECadastrarCliente()
Cliente clienteSalvo = clienteIntegracaoService.processarECadastrarCliente(dto);
```

### 3. Determinação do Tipo
```java
// Determina se é PF ou PJ baseado no CPF/CNPJ
boolean isPessoaFisica = cpfCnpj.length() <= 11;
```

### 4. Processamento por Tipo de Manutenção

#### Inclusão (I)
- Verifica se cliente já existe
- Cria novo registro
- Configurações padrão

#### Alteração (A)
- Busca cliente existente
- **Gerencia entidades relacionadas** (endereços, contas, capacidade financeira, etc.)
- Copia o ID para o objeto da integração
- Substitui completamente o objeto na base
- **Mantém o ID original e preserva relacionamentos**

#### Exclusão (E)
- Busca cliente existente
- Remove da base de dados

## Preservação do ID

**Ponto importante**: O sistema **NÃO** modifica o ID do cliente durante alterações. Isso é garantido por:

1. **Busca do cliente existente**:
```java
Optional<ClientePf> clienteExistenteOpt = clienteRepository.findByCpf(clienteIntegrado.getCpf());
ClientePf clienteAtual = clienteExistenteOpt.get();
```

2. **Cópia do ID para o objeto da integração**:
```java
clienteIntegrado.setId(clienteAtual.getId());
```

3. **Salvamento do objeto da integração com ID original**:
```java
ClientePf clienteSalvo = clienteRepository.save(clienteIntegrado);
```

## Gerenciamento de Entidades Relacionadas

O sistema implementa uma lógica sofisticada para gerenciar entidades relacionadas, baseada no `alterarClientePorIntegracao` do sistema legado:

### Capacidade Financeira
```java
// Verifica se existe capacidade financeira do tipo
if (clienteIntegrado.getRendamensal() != null && clienteIntegrado.getRendamensal().compareTo(BigDecimal.ZERO) > 0) {
    alterarCapacidadeFinanceiraPorTipo(clienteAtual, "RENDA_BRUTA_MENSAL", clienteIntegrado.getRendamensal());
} else {
    removerCapacidadeFinanceiraPorTipo(clienteAtual, "RENDA_BRUTA_MENSAL");
}
```

### Endereços
```java
// Verifica se endereço existe e atualiza, ou cria novo
for (Endereco enderecoIntegrado : clienteIntegrado.getEnderecos()) {
    boolean existeEndereco = false;
    for (Endereco enderecoAtual : clienteAtual.getEnderecos()) {
        if (enderecosIguais(enderecoAtual, enderecoIntegrado)) {
            atualizarEndereco(enderecoAtual, enderecoIntegrado);
            existeEndereco = true;
            break;
        }
    }
    if (!existeEndereco) {
        enderecoIntegrado.setCliente(clienteAtual);
        clienteAtual.getEnderecos().add(enderecoIntegrado);
    }
}
```

### Contas Correntes
```java
// Atualiza contas existentes ou adiciona novas
for (ContaCorrente contaIntegrada : clienteIntegrado.getContaCorrenteVOs()) {
    boolean existe = false;
    for (ContaCorrente contaAtual : clienteAtual.getContaCorrenteVOs()) {
        if (contasCorrentesIguais(contaAtual, contaIntegrada)) {
            atualizarContaCorrente(contaAtual, contaIntegrada);
            existe = true;
            break;
        }
    }
    if (!existe) {
        contaIntegrada.setCliente(clienteAtual);
        clienteAtual.getContaCorrenteVOs().add(contaIntegrada);
    }
}
```

## Substituição Completa

A nova abordagem substitui completamente o objeto, sendo mais eficiente:

```java
// Gerencia entidades relacionadas primeiro
gerenciarEntidadesRelacionadasPf(clienteAtual, clienteIntegrado);

// Copia o ID do cliente existente
clienteIntegrado.setId(clienteAtual.getId());

// Configurações padrão após alteração
clienteIntegrado.setVerificadoCompliance(false);
clienteIntegrado.setDesabilitado(false);
clienteIntegrado.setDataDeDesativacao(null);

// Salva o objeto completo da integração
clienteRepository.save(clienteIntegrado);
```

## Tipos de Manutenção

- **I** - Inclusão: Cria novo cliente
- **A** - Alteração: Atualiza cliente existente
- **E** - Exclusão: Remove cliente

## Estrutura de Dados

### ClienteRequestDTO
```json
{
  "cliente": {
    "nome": "João Silva",
    "cpfCnpj": "12345678901",
    "cliTipoDeManutencao": "A",
    "clientePf": {
      "sexo": "M",
      "dataDeNascimento": "15051985",
      "identidade": "123456789",
      // ... outros campos PF
    }
  }
}
```

## Configurações

### Propriedades de Retry
```yaml
app:
  pubsub:
    retry:
      max-attempts: 3
      delay-ms: 1000
      backoff-multiplier: 2.0
```

### Dead Letter Queue
```yaml
app:
  pubsub:
    dead-letter:
      enabled: true
      topic: cliente-cadastro-dlq
```

## Logs e Monitoramento

O sistema gera logs detalhados para:
- Início do processamento
- Tipo de operação (I/A/E)
- Sucesso/falha das operações
- IDs dos clientes processados

## Validações

- Verificação de duplicidade por CPF/CNPJ
- Validação de tipo de manutenção
- Verificação de existência para alteração/exclusão

## Tratamento de Erros

- Retry automático com backoff exponencial
- Dead letter queue para mensagens com falha
- Logs detalhados de erros
- Rollback de transações em caso de falha

## Exemplo de Uso

```java
// Mensagem recebida do Pub/Sub
{
  "messageId": "msg-123",
  "tipoEvento": "CLIENTE_CADASTRO",
  "dados": {
    "cliente": {
      "nome": "João Silva",
      "cpfCnpj": "12345678901",
      "cliTipoDeManutencao": "A",
      "clientePf": {
        "sexo": "M",
        "dataDeNascimento": "15051985"
      }
    }
  }
}

// Resultado: Cliente alterado mantendo ID original
```

## Diferenças do Sistema Legado

1. **Microserviço**: Arquitetura distribuída vs monolítica
2. **Pub/Sub**: Comunicação assíncrona vs síncrona
3. **Spring Boot**: Framework moderno vs legacy
4. **JPA/Hibernate**: ORM moderno vs Hibernate legacy
5. **Logging**: SLF4J vs Log4J
6. **Validação**: Spring Validation vs validação customizada

## Entidades Relacionadas Gerenciadas

### Para Cliente PF e PJ:
- ✅ **Capacidade Financeira** - Atualiza valores existentes ou cria novos
- ✅ **Endereços** - Atualiza existentes ou adiciona novos
- ✅ **Contas Correntes** - Atualiza existentes ou adiciona novas
- ✅ **Operações Permitidas** - Gerencia inclusão/exclusão por tipo
- ✅ **Corretoras** - Substitui lista completa
- ✅ **Representantes Legais** - Substitui lista completa
- ✅ **Emails para Documentos** - Substitui lista completa

### Específico para Cliente PJ:
- ✅ **Sócios Acionistas** - Substitui lista completa
- ✅ **Contatos** - Substitui lista completa

## Próximos Passos

1. ✅ Implementar gerenciamento de entidades relacionadas
2. ✅ Implementar lógica de capacidade financeira
3. ✅ Implementar comparação inteligente de entidades
4. 🔄 Implementar repositórios para tipos de capacidade
5. 🔄 Implementar validações específicas por entidade
6. 🔄 Implementar logs detalhados de alterações
7. 🔄 Implementar testes para entidades relacionadas
8. 🔄 Implementar rollback em caso de falha
9. 🔄 Implementar auditoria de mudanças

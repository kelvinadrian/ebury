# Estrutura de Repositórios e Serviços

## Visão Geral

A estrutura foi reorganizada para separar responsabilidades e facilitar a manutenção. A lógica de conversão foi movida do `PubSubConsumerService` para o `ClienteService`, e foram criados repositórios específicos para cada entidade relacionada.

## Arquitetura de Serviços

### PubSubConsumerService
**Responsabilidade**: Processamento de mensagens do Pub/Sub
- Recebe mensagens da fila
- Implementa retry automático
- Gerencia dead letter queue
- Delega processamento para ClienteService

### ClienteService
**Responsabilidade**: Lógica de negócio e conversão
- Converte DTO para entidades
- Busca/cria entidades relacionadas
- Salva cliente no banco
- Gerencia relacionamentos

## Repositórios Criados

### Repositórios Básicos
```java
// Entidades de domínio
SexoRepository
EstadoCivilRepository
NacionalidadeRepository
TipoDeIdentificacaoRepository
PorteDoClienteRepository
RamoDeAtividadeRepository
NaturezaJuridicaRepository
OriginadorRepository
UsuarioRepository
TipoDePessoaRepository
```

### Métodos de Busca
Cada repositório implementa métodos específicos:

```java
// Exemplo: SexoRepository
Optional<Sexo> findByCodigo(String codigo);
Optional<Sexo> findByDescricao(String descricao);

// Exemplo: UsuarioRepository
Optional<Usuario> findByLogin(String login);
Optional<Usuario> findByNome(String nome);
```

## Fluxo de Processamento

### 1. Recebimento da Mensagem
```java
@ServiceActivator(inputChannel = "clienteCadastroInputChannel")
public void processarMensagemCliente(String payload, BasicAcknowledgeablePubsubMessage message) {
    // Parse da mensagem
    // Extração do DTO
    // Delegação para ClienteService
}
```

### 2. Conversão no ClienteService
```java
public Cliente processarECadastrarCliente(ClienteRequestDTO dto) {
    // Converter DTO para entidade
    Cliente cliente = converterParaEntidade(dto);
    
    // Salvar no banco
    return clienteRepository.save(cliente);
}
```

### 3. Busca de Entidades Relacionadas
```java
public Sexo obterSexo(String codigo) {
    // 1. Busca por código
    Sexo sexo = sexoRepository.findByCodigo(codigo).orElse(null);
    
    // 2. Se não encontrar, busca por descrição
    if (sexo == null) {
        String descricao = "M".equals(codigo) ? "Masculino" : "Feminino";
        sexo = sexoRepository.findByDescricao(descricao).orElse(null);
    }
    
    // 3. Se não encontrar, cria novo
    if (sexo == null) {
        sexo = new Sexo();
        sexo.setCodigo(codigo);
        sexo.setDescricao(descricao);
        return sexoRepository.save(sexo);
    }
    
    return sexo;
}
```

## Estratégia de Busca/Criação

### Padrão Implementado
1. **Busca Primária**: Por código ou identificador único
2. **Busca Secundária**: Por descrição ou nome
3. **Criação**: Se não existir, cria nova entidade

### Exemplo Completo
```java
public EstadoCivil obterEstadoCivil(String descricao) {
    if (descricao == null) return null;
    
    // Busca existente
    EstadoCivil estadoCivil = estadoCivilRepository.findByDescricao(descricao).orElse(null);
    if (estadoCivil != null) return estadoCivil;
    
    // Cria novo se não existir
    estadoCivil = new EstadoCivil();
    estadoCivil.setDescricao(descricao);
    return estadoCivilRepository.save(estadoCivil);
}
```

## Vantagens da Nova Estrutura

### 1. Separação de Responsabilidades
- **PubSubConsumerService**: Foco em mensageria
- **ClienteService**: Foco em lógica de negócio
- **Repositórios**: Foco em acesso a dados

### 2. Reutilização
- Métodos de busca podem ser usados em outros serviços
- Lógica de conversão centralizada
- Fácil extensão para novos tipos

### 3. Manutenibilidade
- Código mais organizado
- Responsabilidades claras
- Fácil de testar

### 4. Performance
- Busca otimizada por índices
- Cache automático do Spring Data
- Queries específicas por entidade

## Próximos Passos

### Implementações Pendentes
1. **Cache**: Implementar cache para entidades básicas
2. **Validações**: Adicionar validações específicas
3. **Auditoria**: Implementar auditoria de mudanças
4. **Batch Processing**: Processamento em lote

### Melhorias Sugeridas
1. **Async Processing**: Processamento assíncrono
2. **Event Sourcing**: Rastreamento de mudanças
3. **CQRS**: Separação de leitura/escrita
4. **Saga Pattern**: Transações distribuídas

## Exemplo de Uso

### Cliente PF
```json
{
  "cliente": {
    "nome": "João Silva",
    "cpfCnpj": "12345678901",
    "clientePf": {
      "sexo": "M",
      "estadoCivil": "SOLTEIRO",
      "nacionalidade": "BRASILEIRA"
    }
  }
}
```

### Processamento
1. **PubSubConsumerService** recebe mensagem
2. **ClienteService** converte DTO
3. **obterSexo("M")** busca/cria entidade Sexo
4. **obterEstadoCivil("SOLTEIRO")** busca/cria entidade EstadoCivil
5. **obterNacionalidade("BRASILEIRA")** busca/cria entidade Nacionalidade
6. **ClienteRepository** salva cliente completo

## Configurações

### application.yml
```yaml
app:
  pubsub:
    retry:
      max-attempts: 3
      delay-ms: 1000
      backoff-multiplier: 2.0
    dead-letter:
      enabled: true
      topic: cliente-cadastro-dlq
```

### Logs
```log
INFO  - Iniciando processamento da mensagem. MessageId: abc-123, Tentativa: 1
INFO  - Processando cadastro do cliente: João Silva
INFO  - Cliente cadastrado com sucesso. ID: 456, Nome: João Silva, Tipo: PF
INFO  - Cliente processado com sucesso. ID: 456, MessageId: abc-123, Tentativa: 1
``` 
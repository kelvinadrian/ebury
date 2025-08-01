# Sistema de Validação por Anotações

Este sistema implementa um framework de validação customizado baseado em anotações Java, permitindo validação extensível e ordenada de campos em DTOs.

## Como Funciona

O sistema utiliza:
- **Anotações customizadas** para marcar campos que devem ser validados
- **Validadores específicos** que implementam a interface `FieldValidator`
- **Ordem de validação** controlada pelo parâmetro `ordem` nas anotações
- **Reflection** para descobrir automaticamente campos anotados
- **Injeção de dependência** do Spring para gerenciar validadores

## Anotações Disponíveis

### Campos Principais do Cliente
- `@ValidacaoTipoDeManutencao(ordem = 1)` - Tipo de manutenção do cliente
- `@ValidacaoTipoDePessoa(ordem = 2)` - Tipo de pessoa (Física/Jurídica)
- `@ValidacaoCpfCnpj(ordem = 3)` - Validação de CPF/CNPJ
- `@ValidacaoCodigoCorporativo(ordem = 4)` - Código corporativo
- `@ValidacaoNome(ordem = 5)` - Nome do cliente
- `@ValidacaoData(ordem = 6)` - Data do cadastro
- `@ValidacaoDataDesativacao(ordem = 7)` - Data de desativação
- `@ValidacaoDesabilitado(ordem = 8)` - Status desabilitado
- `@ValidacaoAssinaturaDigital(ordem = 9)` - Uso de assinatura digital
- `@ValidacaoNegociacao(ordem = 10)` - Negociação
- `@ValidacaoComplementoNatureza(ordem = 11)` - Complemento da natureza
- `@ValidacaoNaturezaJuridica(ordem = 12, nivel = 1)` - Natureza jurídica nível 1
- `@ValidacaoNaturezaJuridica(ordem = 13, nivel = 2)` - Natureza jurídica nível 2
- `@ValidacaoOriginador(ordem = 14)` - Originador
- `@ValidacaoTipoResidencia(ordem = 15)` - Tipo de residência
- `@ValidacaoGerenteAnalista(ordem = 16)` - Gerente analista
- `@ValidacaoPep(ordem = 18)` - PEP (Pessoa Exposta Politicamente)
- `@ValidacaoIban(ordem = 28)` - IBAN

### Campos de Pessoa Física
- `@ValidacaoSexo(ordem = 29)` - Sexo
- `@ValidacaoEstadoCivil(ordem = 30)` - Estado civil
- `@ValidacaoData(ordem = 31)` - Data de nascimento
- `@ValidacaoDocumentoIdentificacao(ordem = 32)` - Documento de identificação
- `@ValidacaoEmissorDocumento(ordem = 33)` - Emissor do documento
- `@ValidacaoUfEmissor(ordem = 34)` - UF do emissor
- `@ValidacaoData(ordem = 35)` - Data do documento
- `@ValidacaoNomeMae(ordem = 36)` - Nome da mãe
- `@ValidacaoNomePai(ordem = 37)` - Nome do pai
- `@ValidacaoNacionalidade(ordem = 38)` - Nacionalidade
- `@ValidacaoMunicipioNaturalidade(ordem = 39)` - Município da naturalidade
- `@ValidacaoUfNaturalidade(ordem = 40)` - UF da naturalidade
- `@ValidacaoNomeConjuge(ordem = 41)` - Nome do cônjuge
- `@ValidacaoTelefone(ordem = 42)` - Telefone residencial
- `@ValidacaoTelefone(ordem = 43)` - Telefone comercial
- `@ValidacaoTelefone(ordem = 44)` - Telefone celular
- `@ValidacaoRenda(ordem = 45)` - Renda mensal
- `@ValidacaoRenda(ordem = 46)` - Patrimônio

### Campos de Pessoa Jurídica
- `@ValidacaoInscricaoEstadual(ordem = 47)` - Inscrição estadual
- `@ValidacaoUfEmissor(ordem = 48)` - UF do emissor da inscrição
- `@ValidacaoPorte(ordem = 49)` - Porte da empresa
- `@ValidacaoRamoAtividade(ordem = 50)` - Ramo de atividade
- `@ValidacaoFaturamentoMedio(ordem = 51)` - Faturamento médio mensal

### Listas Complexas
- `@ValidacaoEndereco(ordem = 19)` - Lista de endereços
- `@ValidacaoEnderecoNoExterior(ordem = 20)` - Lista de endereços no exterior
- `@ValidacaoContaCorrente(ordem = 21)` - Lista de contas correntes
- `@ValidacaoContaCorrenteTedDoc(ordem = 22)` - Lista de contas TED/DOC
- `@ValidacaoEmailsDocumentos(ordem = 23)` - Lista de emails para documentos
- `@ValidacaoOperacoesPermitidas(ordem = 24)` - Lista de operações permitidas
- `@ValidacaoCorretorasQueRepresentam(ordem = 25)` - Lista de corretoras
- `@ValidacaoListaDocumentos(ordem = 26)` - Lista de documentos
- `@ValidacaoRepresentanteLegal(ordem = 27)` - Lista de representantes legais

### Anotações com Parâmetros Adicionais
- `@ValidacaoData(formato = "dd/MM/yyyy")` - Formato da data
- `@ValidacaoRenda(valorMinimo = 0, valorMaximo = 1000000)` - Valores mínimo e máximo
- `@ValidacaoFaturamentoMedio(valorMinimo = 0, valorMaximo = Integer.MAX_VALUE)` - Valores mínimo e máximo
- `@ValidacaoNaturezaJuridica(nivel = 1)` - Nível da natureza jurídica

## Como Usar

### 1. Anotar Campos no DTO

```java
public class ClienteDTO {
    @ValidacaoTipoDeManutencao(ordem = 1)
    private String cliTipoDeManutencao;
    
    @ValidacaoCpfCnpj(ordem = 3)
    private String cpfCnpj;
    
    @ValidacaoNome(ordem = 5)
    private String nome;
    
    @ValidacaoData(ordem = 6)
    private String dataDoCadastro;
}
```

### 2. Criar Novas Anotações

```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoNovoCampo {
    int ordem() default 100;
    String formato() default "";
}
```

### 3. Criar Validadores Correspondentes

```java
@Component
@Slf4j
public class NovoCampoValidator implements FieldValidator {
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoNovoCampo.class) == null) {
            return ValidationResult.valid();
        }
        
        // Implementar regras de validação específicas
        // TODO: Adicionar regras de negócio
        
        return ValidationResult.valid();
    }
}
```

## Como Funciona a Ordem de Validação

A validação é executada na ordem especificada pelo parâmetro `ordem`:

- **Menor número = Maior prioridade** (ordem = 1 executa antes de ordem = 2)
- **Campos com mesma ordem** são ordenados alfabeticamente por `fieldPath`
- **Ordem padrão** é 100 para campos sem anotação

### Exemplos de Ordenação

#### Sequencial (1, 2, 3, 4...)
```java
@ValidacaoTipoDeManutencao(ordem = 1)  // Primeiro
@ValidacaoTipoDePessoa(ordem = 2)      // Segundo
@ValidacaoCpfCnpj(ordem = 3)           // Terceiro
@ValidacaoNome(ordem = 4)              // Quarto
```

#### Categórico (10, 20, 30...)
```java
@ValidacaoTipoDeManutencao(ordem = 10) // Dados básicos
@ValidacaoTipoDePessoa(ordem = 20)     // Dados básicos
@ValidacaoCpfCnpj(ordem = 30)          // Dados básicos
@ValidacaoNome(ordem = 40)             // Dados pessoais
```

#### Prioridade de Negócio
```java
@ValidacaoCpfCnpj(ordem = 1)           // Crítico - validar primeiro
@ValidacaoNome(ordem = 5)              // Importante
@ValidacaoTelefone(ordem = 10)         // Menos crítico
```

## Exemplo de Uso

### JSON de Entrada
```json
{
  "cliente": {
    "cliTipoDeManutencao": "I",
    "tipoDePessoa": "F",
    "cpfCnpj": "12345678901",
    "nome": "João Silva",
    "dataDoCadastro": "01/01/2024"
  }
}
```

### Resposta de Validação
```json
{
  "inconsistencias": [
    {
      "campo": "cliente.cpfCnpj",
      "mensagem": "CPF inválido",
      "ordem": 3
    },
    {
      "campo": "cliente.nome",
      "mensagem": "Nome deve ter pelo menos 3 caracteres",
      "ordem": 5
    }
  ]
}
```

## Vantagens do Sistema

1. **Simplicidade**: Apenas anote os campos que deseja validar
2. **Clareza**: Cada validador tem responsabilidade específica
3. **Flexibilidade**: Fácil adicionar novos tipos de validação
4. **Reutilização**: Validadores podem ser usados em múltiplos campos
5. **Extensibilidade**: Novos validadores são descobertos automaticamente
6. **Manutenibilidade**: Regras de validação centralizadas por campo
7. **Ordenação**: Controle total sobre a ordem de execução das validações

## Estrutura de Arquivos

```
validation/
├── annotations/           # Anotações customizadas
│   ├── ValidacaoNome.java
│   ├── ValidacaoCpfCnpj.java
│   └── ...
├── validators/           # Implementações dos validadores
│   ├── NomeValidator.java
│   ├── CpfCnpjValidator.java
│   └── ...
├── FieldValidator.java   # Interface base
├── ValidationResult.java # Resultado da validação
├── ValidationService.java # Serviço principal
└── README.md            # Esta documentação
```

## Próximos Passos

1. **Implementar regras de negócio** em cada validador
2. **Adicionar testes unitários** para cada validador
3. **Criar validações cross-field** quando necessário
4. **Implementar cache** para melhorar performance
5. **Adicionar validações condicionais** baseadas em outros campos 
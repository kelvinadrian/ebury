# Estrutura de Entidades - Sistema de Cadastro de Clientes

## Visão Geral

O sistema foi adaptado para trabalhar com uma estrutura de herança complexa baseada no modelo existente, com suporte a **Pessoa Física (PF)** e **Pessoa Jurídica (PJ)**.

## Hierarquia de Entidades

```
Cliente (Base)
├── ClientePf (Pessoa Física)
└── ClientePj (Pessoa Jurídica)
```

## Entidade Base: Cliente

### Características
- **Herança**: `@Inheritance(strategy = InheritanceType.JOINED)`
- **Cache**: `@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)`
- **Dynamic Updates**: `@Entity(dynamicUpdate = true, dynamicInsert = true)`

### Campos Principais
```java
@Column(length = 250, nullable = false)
private String nome;

@Column(length = 20)
private String codigoExterno;

@Temporal(TemporalType.TIMESTAMP)
private Date dataDeCadastro;

@ManyToOne(fetch = FetchType.EAGER)
private TipoDePessoa tipoDePessoa;

@Column(name = "codigocorporativo")
private String codigoCorporativo;

@Column(name = "iban", length = 30, nullable = true)
private String iban;
```

### Relacionamentos Principais
- **Endereços**: `@OneToMany` → `List<Endereco>`
- **Contas Correntes**: `@OneToMany` → `List<ContaCorrente>`
- **Histórico**: `@OneToMany` → `List<HistoricoDoCliente>`
- **Compliance**: `@OneToMany` → `List<ComplianceDoCliente>`

## ClientePf (Pessoa Física)

### Campos Específicos
```java
@Column(length = 14, nullable = false)
private String cpf;

@ManyToOne
@JoinColumn(name = "sexo_id")
private Sexo sexo;

@Temporal(TemporalType.TIMESTAMP)
private Date dataDeNascimento;

@Column(length = 40)
private String identidade;

@Column(length = 40)
private String orgaoEmissor;

@Column(length = 50, nullable = true)
private String nomeDaMae;

@Column(length = 50, nullable = true)
private String nomeDoPai;

@Column(precision = 19, scale = 4, nullable = true)
private BigDecimal rendamensal;

@Column(precision = 19, scale = 4, nullable = true)
private BigDecimal patrimonio;
```

### Relacionamentos
- **Sexo**: `@ManyToOne` → `Sexo`
- **Estado Civil**: `@ManyToOne` → `EstadoCivil`
- **Nacionalidade**: `@ManyToOne` → `Nacionalidade`
- **Tipo de Identificação**: `@ManyToOne` → `TipoDeIdentificacao`

## ClientePj (Pessoa Jurídica)

### Campos Específicos
```java
@Column(length = 18, nullable = false)
private String cnpj;

@Column(length = 20)
private String inscricaoEstadual;

@Column(length = 2)
private String estadoDeEmissao;

@Column(length = 50, nullable = true)
private String razaoSocial;

@Column(name = "faturamentomediomensal", precision = 19, scale = 4, nullable = true)
private BigDecimal faturamentomediomensal;
```

### Relacionamentos
- **Porte**: `@ManyToOne` → `PorteDoCliente`
- **Ramo de Atividade**: `@ManyToOne` → `RamoDeAtividade`
- **Contatos**: `@OneToMany` → `List<ContatoDoCliente>`

## Entidades de Suporte

### Entidades Básicas
- **Sexo**: Mapeamento de sexo (M/F)
- **EstadoCivil**: Solteiro, Casado, etc.
- **Nacionalidade**: Brasileira, Americana, etc.
- **TipoDeIdentificacao**: RG, CPF, etc.
- **PorteDoCliente**: Micro, Pequena, Média, Grande
- **RamoDeAtividade**: Comércio, Indústria, etc.

### Entidades de Relacionamento
- **Endereco**: Endereços do cliente
- **ContaCorrente**: Contas bancárias
- **ContaCorrenteME**: Contas em moeda estrangeira
- **ContaCorrenteTedDoc**: Contas para TED/DOC
- **ContatoDoCliente**: Contatos de PJ

## Processamento no Consumer

### Lógica de Conversão
```java
// Determinar se é PF ou PJ baseado no CPF/CNPJ
String cpfCnpj = clienteData.getCpfCnpj();
boolean isPessoaFisica = cpfCnpj != null && cpfCnpj.length() <= 14;

if (isPessoaFisica) {
    ClientePf clientePf = new ClientePf();
    // Preencher dados específicos de PF
} else {
    ClientePj clientePj = new ClientePj();
    // Preencher dados específicos de PJ
}
```

### Métodos Auxiliares
- `obterSexo(String codigo)`: Busca ou cria entidade Sexo
- `obterEstadoCivil(String descricao)`: Busca ou cria entidade EstadoCivil
- `obterNacionalidade(String descricao)`: Busca ou cria entidade Nacionalidade
- `converterData(String data)`: Converte string DDMMYYYY para Date
- `converterUF(String uf)`: Converte string para enum UF

## Configurações de Banco

### Estratégia de Herança
```java
@Inheritance(strategy = InheritanceType.JOINED)
```
- **JOINED**: Cada classe tem sua própria tabela
- **Tabela Cliente**: Campos comuns
- **Tabela ClientePf**: Campos específicos de PF
- **Tabela ClientePj**: Campos específicos de PJ

### Cache
```java
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
```
- **READ_WRITE**: Permite leitura e escrita
- **Performance**: Melhora performance de consultas frequentes

### Dynamic Updates
```java
@Entity(dynamicUpdate = true, dynamicInsert = true)
```
- **dynamicUpdate**: Só atualiza campos modificados
- **dynamicInsert**: Só insere campos não-nulos

## Próximos Passos

### Implementações Pendentes
1. **Repositórios**: Criar repositórios para entidades relacionadas
2. **Cache**: Implementar cache para entidades básicas
3. **Validações**: Adicionar validações específicas por tipo
4. **Consultas**: Otimizar consultas com relacionamentos
5. **Auditoria**: Implementar auditoria de mudanças

### Melhorias Sugeridas
1. **Lazy Loading**: Configurar carregamento lazy para relacionamentos
2. **Batch Processing**: Implementar processamento em lote
3. **Indexes**: Criar índices para campos frequentes
4. **Partitioning**: Considerar particionamento para grandes volumes

## Exemplo de Uso

### Cliente PF
```json
{
  "cliente": {
    "nome": "João Silva",
    "cpfCnpj": "12345678901",
    "tipoDePessoa": "PF",
    "clientePf": {
      "sexo": "M",
      "dataDeNascimento": "15011990",
      "estadoCivil": "SOLTEIRO",
      "nacionalidade": "BRASILEIRA"
    }
  }
}
```

### Cliente PJ
```json
{
  "cliente": {
    "nome": "Empresa XYZ Ltda",
    "cpfCnpj": "12345678000199",
    "tipoDePessoa": "PJ",
    "clientePj": {
      "inscricaoEstadual": "123456789",
      "porte": "MEDIA",
      "ramoDeAtividade": "COMERCIO"
    }
  }
}
``` 
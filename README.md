# Ebury Cadastro Cliente - Microserviços

Sistema de cadastro de clientes com arquitetura de microserviços usando Spring Boot, Java 21 e Google Cloud Pub/Sub.

## 🏗️ Arquitetura

O sistema é composto por **2 microserviços independentes**:

### **1. Producer API** (Porta 8080)
- **Responsabilidade**: Receber requisições de cadastro e enviar para fila
- **Endpoint**: `POST /api/v1/clientes/cadastro`
- **Tecnologias**: Spring Boot, Google Cloud Pub/Sub, Swagger/OpenAPI
- **Banco**: Não utiliza banco de dados
- **Deploy**: Independente em ambiente próprio
- **Documentação**: Swagger UI disponível em `/swagger-ui.html`

### **2. Consumer Service** (Porta 8081)
- **Responsabilidade**: Consumir mensagens da fila e salvar no banco de dados
- **Funcionalidade**: Processamento assíncrono de cadastros
- **Tecnologias**: Spring Boot, JPA, SQL Server, Google Cloud Pub/Sub
- **Banco**: SQL Server
- **Deploy**: Independente em ambiente próprio

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Google Cloud Pub/Sub**
- **SQL Server**
- **Docker & Kubernetes**
- **Lombok**
- **Maven**
- **Swagger/OpenAPI 3** (apenas Producer)

## 📁 Estrutura do Projeto

```
├── producer/                    # Microserviço Producer
│   ├── src/main/java/...
│   ├── k8s/                    # Configurações Kubernetes do Producer
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── ingress.yaml
│   │   └── secret.yaml
│   ├── Dockerfile
│   ├── pom.xml
│   └── deploy.sh               # Script de deploy do Producer
├── consumer/                    # Microserviço Consumer
│   ├── src/main/java/...
│   ├── k8s/                    # Configurações Kubernetes do Consumer
│   │   ├── configmap.yaml
│   │   ├── deployment.yaml
│   │   ├── service.yaml
│   │   ├── ingress.yaml
│   │   └── secret.yaml
│   ├── Dockerfile
│   ├── pom.xml
│   └── deploy.sh               # Script de deploy do Consumer
├── docker-compose.yml          # Desenvolvimento local
└── README.md                   # Documentação completa
```

## 🚀 Como Executar

### Desenvolvimento Local

1. **Clone o repositório:**
   ```bash
   git clone <repository-url>
   cd ebury-cadastro-cliente
   ```

2. **Execute com Docker Compose:**
   ```bash
   docker-compose up --build
   ```

3. **Ou execute individualmente:**
   ```bash
   # Producer
   cd producer && mvn spring-boot:run
   
   # Consumer (em outro terminal)
   cd consumer && mvn spring-boot:run
   ```

### Produção com Kubernetes

#### Deploy do Producer

1. **Configure as credenciais do Google Cloud:**
   ```bash
   # Gere o base64 das suas credenciais
   cat your-gcp-credentials.json | base64
   
   # Atualize o arquivo producer/k8s/secret.yaml com o resultado
   ```

2. **Atualize o ConfigMap do Producer:**
   ```bash
   # Edite producer/k8s/configmap.yaml
   ```

3. **Deploy do Producer:**
   ```bash
   cd producer
   chmod +x deploy.sh
   ./deploy.sh
   ```

#### Deploy do Consumer

1. **Configure as credenciais do Google Cloud:**
   ```bash
   # Gere o base64 das suas credenciais
   cat your-gcp-credentials.json | base64
   
   # Atualize o arquivo consumer/k8s/secret.yaml com o resultado
   ```

2. **Atualize o ConfigMap do Consumer:**
   ```bash
   # Edite consumer/k8s/configmap.yaml
   ```

3. **Deploy do Consumer:**
   ```bash
   cd consumer
   chmod +x deploy.sh
   ./deploy.sh
   ```

## 📚 Endpoints da API

### Producer API (Porta 8080)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/clientes/cadastro` | Cadastrar cliente (envia para fila) |
| GET | `/api/v1/clientes/health` | Health check |
| GET | `/swagger-ui.html` | **Documentação Swagger UI** |
| GET | `/api-docs` | **Especificação OpenAPI JSON** |

### Consumer Service (Porta 8081)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/actuator/health` | Health check |

## 📖 Documentação da API

### Swagger UI
A documentação interativa da API está disponível em:
- **Desenvolvimento**: http://localhost:8080/swagger-ui.html
- **Produção**: https://producer.cadastro-cliente.local/swagger-ui.html

### OpenAPI Specification
A especificação OpenAPI está disponível em:
- **Desenvolvimento**: http://localhost:8080/api-docs
- **Produção**: https://producer.cadastro-cliente.local/api-docs

## 📋 Exemplo de Uso

### 1. Cadastrar Cliente

```bash
curl -X POST http://localhost:8080/api/v1/clientes/cadastro \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": {
      "cliTipoDeManutencao": "s",
      "tipoDePessoa": "st",
      "cpfCnpj": "12345678901",
      "codExterno": "CLI001",
      "codCorporativo": "CORP001",
      "nome": "João Silva",
      "dataDoCadastro": "15012024",
      "dataDeDesativacao": "",
      "desabilitado": "n",
      "utilizaAssinaturaDigital": "s",
      "negociacao": "NEG001",
      "complementoDaNatureza": 2,
      "naturezaJuridicaN1": 1,
      "naturezaJuridicaN2": 2,
      "originador": "ORIG001",
      "tipoDeResidencia": "RESIDENCIAL",
      "gerenteAnalista": "GER001",
      "gerenteAnalistaOriginador": "GER001",
      "pep": "n",
      "iban": "BR1234567890123456789012345",
      "listaEnderecos": {
        "endereco": [
          {
            "endTipo": "RESIDENCIAL",
            "endTipoDeLogradouro": "RUA",
            "endLogradouro": "DAS FLORES",
            "endNumero": "123",
            "endComplemento": "APTO 45",
            "endCep": "01234567",
            "endBairro": "CENTRO",
            "endMunicipio": "SÃO PAULO",
            "endUf": "SP",
            "endPreferencial": true
          }
        ]
      },
      "clientePf": {
        "sexo": "M",
        "estadoCivil": "SOLTEIRO",
        "dataDeNascimento": "15011990",
        "descrDocumIdentifcacao": "RG",
        "documIdentificacao": "12345678",
        "emissorDocumIdentificacao": "SSP",
        "ufEmissorDocumIdentificacao": "SP",
        "dataDocumIdentificacao": "15012010",
        "nomeDaMae": "Maria Silva",
        "nomeDoPai": "José Silva",
        "nacionalidade": "BRASILEIRA",
        "municipioDaNaturalidade": "SÃO PAULO",
        "ufDaNaturalidade": "SP",
        "nomeDoConjuge": "",
        "telefoneResidencial": 11999999999,
        "telefoneComercial": 11888888888,
        "telefoneCelular": 11777777777,
        "rendaMensal": 10000,
        "patrimonio": 50000
      },
      "listaDeDocumentos": [
        {
          "tipoDocumentoDoCliente": "RG",
          "dataDoDocumento": "15012010",
          "dataDoVencimento": "15012030",
          "observacoes": "Documento de identidade",
          "idsDosArquivos": "DOC001",
          "tipoDaOperacao": "INSERIR"
        }
      ]
    }
  }'
```

### 2. Resposta de Sucesso (HTTP 200)

```json
{
  "idDeClienteTree": 1705312200000
}
```

### 3. Resposta de Erro (HTTP 400)

```json
{
  "listaInconsistencias": {
    "inconsistencia": [
      {
        "atributo": "cpfCnpj",
        "mensagem": "CPF/CNPJ já cadastrado"
      },
      {
        "atributo": "nome",
        "mensagem": "Nome é obrigatório"
      }
    ]
  }
}
```

## 🔧 Configurações

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `SPRING_PROFILES_ACTIVE` | Perfil ativo | `dev` |
| `GCP_PROJECT_ID` | ID do projeto Google Cloud | - |
| `GCP_CREDENTIALS_LOCATION` | Localização das credenciais GCP | - |
| `DB_HOST` | Host do banco de dados | `localhost` |
| `DB_PORT` | Porta do banco de dados | `1433` |
| `DB_NAME` | Nome do banco de dados | `cadastro_cliente` |
| `DB_USERNAME` | Usuário do banco de dados | `sa` |
| `DB_PASSWORD` | Senha do banco de dados | `password` |

### Tópicos e Subscriptions Pub/Sub

- **Tópico**: `cliente-cadastro`
- **Subscription**: `cliente-cadastro-sub`

## 🐳 Docker

### Build das Imagens

```bash
# Producer
cd producer && docker build -t cadastro-cliente-producer:latest .

# Consumer
cd consumer && docker build -t cadastro-cliente-consumer:latest .
```

### Executar Containers

```bash
# Producer
docker run -p 8080:8080 cadastro-cliente-producer:latest

# Consumer
docker run -p 8081:8081 cadastro-cliente-consumer:latest
```

## ☸️ Kubernetes

### Recursos por Microserviço

#### Producer
- **ConfigMap**: Configurações do Producer
- **Secret**: Credenciais do Google Cloud
- **Deployment**: Producer (2 réplicas)
- **Service**: Exposição interna do Producer
- **Ingress**: Roteamento externo para Producer

#### Consumer
- **ConfigMap**: Configurações do Consumer
- **Secret**: Credenciais do Google Cloud
- **Deployment**: Consumer (3 réplicas)
- **Service**: Exposição interna do Consumer
- **Ingress**: Roteamento externo para Consumer

### Aplicar Recursos

```bash
# Producer
cd producer && kubectl apply -f k8s/

# Consumer
cd consumer && kubectl apply -f k8s/
```

### Verificar Status

```bash
# Producer
kubectl get pods -l app=cadastro-cliente-producer
kubectl get services -l app=cadastro-cliente-producer

# Consumer
kubectl get pods -l app=cadastro-cliente-consumer
kubectl get services -l app=cadastro-cliente-consumer
```

## 📊 Monitoramento

### Health Checks

```bash
# Producer
curl http://localhost:8080/api/v1/clientes/health

# Consumer
curl http://localhost:8081/actuator/health
```

### Métricas

```bash
# Producer
curl http://localhost:8080/actuator/metrics

# Consumer
curl http://localhost:8081/actuator/metrics
```

## 🔄 Fluxo de Processamento

1. **Cliente envia requisição** → Producer API (8080)
2. **Producer processa dados** → Validação e preparação
3. **Producer envia para fila** → Google Cloud Pub/Sub
4. **Consumer recebe mensagem** → Processamento assíncrono
5. **Consumer salva no banco** → SQL Server
6. **Consumer confirma processamento** → Acknowledge

## 🧪 Testes

### Executar Testes

```bash
# Producer
cd producer && mvn test

# Consumer
cd consumer && mvn test
```

## 📝 Logs

### Producer
```bash
docker-compose logs producer
```

### Consumer
```bash
docker-compose logs consumer
```

## 🔒 Segurança

- Usuário não-root nos containers
- Secrets para credenciais sensíveis
- Health checks configurados
- Resource limits definidos
- Validação de dados de entrada (a ser implementada)

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📞 Suporte

Para suporte, envie um email para suporte@ebury.com ou abra uma issue no repositório. 
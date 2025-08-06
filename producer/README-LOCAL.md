# 🚀 Ebury Cadastro Cliente - Modo Local

Este documento explica como rodar o projeto localmente sem dependências do Google Cloud.

## ✅ Pré-requisitos

- Java 21
- Maven 3.8+
- cURL (para testes)

## 🎯 Como Rodar Localmente

### Windows (PowerShell)

#### Opção 1: Script Automático (Recomendado)
```powershell
# No diretório producer/
.\start-local.ps1
```

#### Opção 2: Comando Manual
```powershell
# No diretório producer/
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Linux/Mac (Bash)

#### Opção 1: Script Automático (Recomendado)
```bash
# No diretório producer/
chmod +x start-local.sh
./start-local.sh
```

#### Opção 2: Comando Manual
```bash
# No diretório producer/
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🌐 Endpoints Disponíveis

### Health Check
```bash
curl http://localhost:8080/api/v1/clientes/health
```

### Cadastro de Cliente
```bash
curl -X POST http://localhost:8080/api/v1/clientes/cadastro \
  -H "Content-Type: application/json" \
  -d @test-request.json
```

### Swagger UI
Acesse: http://localhost:8080/swagger-ui.html

## 🧪 Testando a API

### Windows (PowerShell)

#### Usando o Script de Teste
```powershell
.\test-api.ps1
```

#### Teste Manual
```powershell
# Health Check
Invoke-RestMethod -Uri "http://localhost:8080/api/v1/clientes/health" -Method GET

# Cadastro de Cliente
$jsonContent = Get-Content -Path "test-request.json" -Raw
$headers = @{ "Content-Type" = "application/json" }
Invoke-RestMethod -Uri "http://localhost:8080/api/v1/clientes/cadastro" -Method POST -Body $jsonContent -Headers $headers
```

### Linux/Mac (Bash)

#### Usando o Script de Teste
```bash
chmod +x test-api.sh
./test-api.sh
```

#### Teste Manual
```bash
# Health Check
curl http://localhost:8080/api/v1/clientes/health

# Cadastro de Cliente
curl -X POST http://localhost:8080/api/v1/clientes/cadastro \
  -H "Content-Type: application/json" \
  -d @test-request.json
```

## 📋 Exemplo de JSON para Teste

O arquivo `test-request.json` contém um exemplo completo de dados de cliente:

```json
{
  "cliente": {
    "cliTipoDeManutencao": "I",
    "tipoDePessoa": "F",
    "cpfCnpj": "12345678901",
    "codCorporativo": "CORP001",
    "nome": "João Silva",
    "dataDoCadastro": "01/01/2024",
    "desabilitado": "N",
    "utilizaAssinaturaDigital": "S",
    "negociacao": "NEG001",
    "complementoDaNatureza": 2,
    "naturezaJuridicaN1": 1,
    "naturezaJuridicaN2": 2,
    "originador": "ORIG001",
    "tipoDeResidencia": "RESIDENCIAL",
    "gerenteAnalista": "GER001",
    "gerenteAnalistaOriginador": "GER001",
    "pep": "N",
    "iban": "BR1234567890123456789012345",
    "clientePf": {
      "sexo": "M",
      "estadoCivil": "SOLTEIRO",
      "dataDeNascimento": "01/01/1990",
      "documIdentificacao": "12345678",
      "emissorDocumIdentificacao": "SSP",
      "ufEmissorDocumIdentificacao": "SP",
      "dataDocumIdentificacao": "01/01/2010",
      "nomeDaMae": "Maria Silva",
      "nomeDoPai": "José Silva",
      "nacionalidade": "BRASILEIRA",
      "municipioDaNaturalidade": "SÃO PAULO",
      "ufDaNaturalidade": "SP",
      "telefoneResidencial": 11999999999,
      "telefoneComercial": 11888888888,
      "telefoneCelular": 11777777777,
      "rendaMensal": 10000,
      "patrimonio": 50000
    }
  }
}
```

## 🔧 Configurações do Modo Local

### Perfil de Desenvolvimento
- **Arquivo**: `application-dev.yml`
- **Pub/Sub**: Desabilitado
- **Logs**: DEBUG para validações
- **Porta**: 8080

### Serviços Mock
- **PubSubProducerService**: Substituído por MockPubSubProducerService
- **Validações**: Funcionando normalmente
- **Swagger**: Disponível em `/swagger-ui.html`

## 📊 Respostas Esperadas

### Sucesso (200)
```json
{
  "idDeClienteTree": 1705312200000
}
```

### Erro de Validação (400)
```json
{
  "listaInconsistencias": {
    "inconsistencia": [
      {
        "atributo": "cpfCnpj",
        "mensagem": "CPF/CNPJ já cadastrado"
      }
    ]
  }
}
```

## 🐛 Troubleshooting

### Erro de Compilação
```bash
# Limpar e recompilar
mvn clean compile
```

### Porta em Uso
```bash
# Verificar processos na porta 8080
netstat -ano | findstr :8080
# ou
lsof -i :8080
```

### Logs Detalhados
Os logs de validação aparecem no console com nível DEBUG.

## 🎯 Próximos Passos

1. ✅ **Testar localmente** - Funcionando
2. 🔄 **Implementar validações específicas** - Em andamento
3. 🔄 **Adicionar testes unitários** - Pendente
4. 🔄 **Configurar Google Cloud** - Para produção

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique os logs no console
2. Teste o health check: `curl http://localhost:8080/api/v1/clientes/health`
3. Acesse o Swagger UI: http://localhost:8080/swagger-ui.html 
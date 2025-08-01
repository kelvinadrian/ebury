# Sistema de Validação com Listas - Identificação de Posições

## Como Funciona a Identificação de Posições em Listas

O sistema de validação foi implementado para automaticamente identificar e reportar a posição exata de itens em listas quando ocorrem erros de validação.

### Estrutura dos Field Paths

Quando uma validação falha em um campo dentro de uma lista, o sistema gera um **field path** que inclui o índice da posição na lista:

```
cliente.listaDeDocumentos[0].dataDoDocumento
cliente.listaDeDocumentos[1].tipoDocumentoDoCliente
cliente.listaDeDocumentos[2].dataDoVencimento
cliente.listaDeDocumentos[3].observacoes
```

### Exemplo Prático

Considere uma lista com 4 documentos:

```json
{
  "cliente": {
    "listaDeDocumentos": [
      {
        "tipoDocumentoDoCliente": "RG",
        "dataDoDocumento": "32/13/2024",  // ❌ Data inválida
        "dataDoVencimento": "01/01/2025",
        "observacoes": "Documento de identidade"
      },
      {
        "tipoDocumentoDoCliente": "CPF",
        "dataDoDocumento": "15/06/2023",  // ✅ Válido
        "dataDoVencimento": "15/06/2033",
        "observacoes": "CPF do cliente"
      },
      {
        "tipoDocumentoDoCliente": "CNH",
        "dataDoDocumento": "20/03/2022",  // ✅ Válido
        "dataDoVencimento": "45/13/2032", // ❌ Data inválida
        "observacoes": "Carteira de motorista"
      },
      {
        "tipoDocumentoDoCliente": "Comprovante",
        "dataDoDocumento": "10/12/2023",  // ✅ Válido
        "dataDoVencimento": "10/12/2024",
        "observacoes": "Conta de luz"
      }
    ]
  }
}
```

### Resultado da Validação

O sistema retornará erros específicos com identificação exata da posição:

```java
List<ValidationResult> results = validationService.validateObject(request);

// Resultados esperados:
// 1. Campo: cliente.listaDeDocumentos[0].dataDoDocumento
//    Mensagem: Data inválida no campo 'cliente.listaDeDocumentos[0].dataDoDocumento'. 
//              Formato esperado: dd/MM/yyyy. Valor recebido: '32/13/2024'
//    Ordem: 53

// 2. Campo: cliente.listaDeDocumentos[2].dataDoVencimento
//    Mensagem: Data inválida no campo 'cliente.listaDeDocumentos[2].dataDoVencimento'. 
//              Formato esperado: dd/MM/yyyy. Valor recebido: '45/13/2032'
//    Ordem: 54
```

### Como Interpretar os Resultados

1. **`cliente.listaDeDocumentos[0]`** = Primeiro item da lista (índice 0)
2. **`cliente.listaDeDocumentos[1]`** = Segundo item da lista (índice 1)
3. **`cliente.listaDeDocumentos[2]`** = Terceiro item da lista (índice 2)
4. **`cliente.listaDeDocumentos[3]`** = Quarto item da lista (índice 3)

### Implementação Técnica

O sistema funciona através de:

1. **Detecção de Coleções**: O `ValidationService` identifica automaticamente quando um campo é uma `Collection` (List, Set, etc.)

2. **Validação com Índices**: Para cada item na coleção, o sistema:
   - Gera um field path com índice: `campo[0]`, `campo[1]`, etc.
   - Valida recursivamente cada item usando esse path
   - Mantém o contexto da posição em toda a validação

3. **Mensagens Específicas**: Os validadores recebem o field path completo e podem gerar mensagens que incluem a posição específica

### Exemplo de Validador

```java
@Component
@Slf4j
public class DataValidator implements FieldValidator {
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoData.class) == null) {
            return ValidationResult.valid();
        }
        
        // fieldPath já inclui o índice da lista se aplicável
        // Ex: "cliente.listaDeDocumentos[2].dataDoDocumento"
        
        if (value == null || value.toString().trim().isEmpty()) {
            return ValidationResult.valid();
        }
        
        String dataStr = value.toString().trim();
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(dataStr, formatter);
            return ValidationResult.valid();
            
        } catch (DateTimeParseException e) {
            // Mensagem inclui o fieldPath completo com índice
            String mensagem = String.format("Data inválida no campo '%s'. " +
                                          "Formato esperado: dd/MM/yyyy. " +
                                          "Valor recebido: '%s'", 
                                          fieldPath, dataStr);
            
            return ValidationResult.invalid(mensagem, fieldPath);
        }
    }
}
```

### Vantagens do Sistema

1. **Precisão**: Identifica exatamente qual item da lista falhou
2. **Clareza**: Mensagens de erro são específicas e informativas
3. **Facilidade de Correção**: O usuário sabe exatamente onde corrigir
4. **Extensibilidade**: Funciona com qualquer tipo de lista aninhada
5. **Ordenação**: Mantém a ordem de validação conforme definido nas anotações

### Casos de Uso

- **Formulários Web**: Mostrar erros específicos para cada item da lista
- **APIs REST**: Retornar erros detalhados para o cliente
- **Logs**: Registrar exatamente onde ocorreram problemas
- **Testes**: Validar comportamento específico de cada posição

### Exemplo de Resposta da API

```json
{
  "inconsistencias": [
    {
      "campo": "cliente.listaDeDocumentos[0].dataDoDocumento",
      "mensagem": "Data inválida no campo 'cliente.listaDeDocumentos[0].dataDoDocumento'. Formato esperado: dd/MM/yyyy. Valor recebido: '32/13/2024'",
      "ordem": 53
    },
    {
      "campo": "cliente.listaDeDocumentos[2].dataDoVencimento",
      "mensagem": "Data inválida no campo 'cliente.listaDeDocumentos[2].dataDoVencimento'. Formato esperado: dd/MM/yyyy. Valor recebido: '45/13/2032'",
      "ordem": 54
    }
  ]
}
```

Este sistema garante que você sempre saiba exatamente qual item da lista e qual campo específico falhou na validação, facilitando a correção de erros e melhorando a experiência do usuário. 
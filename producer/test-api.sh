#!/bin/bash

echo "🧪 Testando API Ebury Cadastro Cliente..."
echo ""

# Testar health check
echo "🔍 Testando Health Check..."
curl -s http://localhost:8080/api/v1/clientes/health
echo ""
echo ""

# Testar cadastro de cliente
echo "📝 Testando Cadastro de Cliente..."
echo "📋 Enviando dados do arquivo test-request.json..."
echo ""

response=$(curl -s -X POST http://localhost:8080/api/v1/clientes/cadastro \
  -H "Content-Type: application/json" \
  -d @test-request.json)

echo "📤 Resposta da API:"
echo "$response" | jq '.' 2>/dev/null || echo "$response"
echo ""

echo "✅ Teste concluído!"
echo "🔗 Swagger UI: http://localhost:8080/swagger-ui.html" 
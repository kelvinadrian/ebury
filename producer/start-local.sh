#!/bin/bash

echo "🚀 Iniciando Ebury Cadastro Cliente Producer em modo LOCAL..."
echo "📋 Configuração:"
echo "   - Porta: 8080"
echo "   - Modo: Desenvolvimento (sem Google Cloud Pub/Sub)"
echo "   - Swagger UI: http://localhost:8080/swagger-ui.html"
echo "   - Health Check: http://localhost:8080/api/v1/clientes/health"
echo ""

# Compilar o projeto
echo "🔨 Compilando o projeto..."
mvn clean compile -q

if [ $? -eq 0 ]; then
    echo "✅ Compilação bem-sucedida!"
    echo ""
    echo "🌐 Iniciando aplicação..."
    echo "📝 Para testar, use o arquivo: test-request.json"
    echo "🔗 Swagger UI: http://localhost:8080/swagger-ui.html"
    echo ""
    
    # Iniciar com perfil de desenvolvimento
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
else
    echo "❌ Erro na compilação!"
    exit 1
fi 
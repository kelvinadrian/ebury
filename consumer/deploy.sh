#!/bin/bash

# Script de deploy para o Consumer
echo "🚀 Iniciando deploy do Consumer..."

# Build da imagem Docker
echo "📦 Fazendo build da imagem Docker..."
docker build -t cadastro-cliente-consumer:latest .

# Aplicar recursos Kubernetes
echo "☸️ Aplicando recursos Kubernetes..."
kubectl apply -f k8s/

# Verificar status
echo "📊 Verificando status do deploy..."
kubectl get pods -l app=cadastro-cliente-consumer
kubectl get services -l app=cadastro-cliente-consumer
kubectl get ingress -l app=cadastro-cliente-consumer

echo "✅ Deploy do Consumer concluído!" 
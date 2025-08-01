#!/bin/bash

# Script de deploy para o Producer
echo "🚀 Iniciando deploy do Producer..."

# Build da imagem Docker
echo "📦 Fazendo build da imagem Docker..."
docker build -t cadastro-cliente-producer:latest .

# Aplicar recursos Kubernetes
echo "☸️ Aplicando recursos Kubernetes..."
kubectl apply -f k8s/

# Verificar status
echo "📊 Verificando status do deploy..."
kubectl get pods -l app=cadastro-cliente-producer
kubectl get services -l app=cadastro-cliente-producer
kubectl get ingress -l app=cadastro-cliente-producer

echo "✅ Deploy do Producer concluído!" 
Write-Host "🚀 Iniciando Ebury Cadastro Cliente Producer em modo LOCAL..." -ForegroundColor Green
Write-Host "📋 Configuração:" -ForegroundColor Yellow
Write-Host "   - Porta: 8080" -ForegroundColor White
Write-Host "   - Modo: Desenvolvimento (sem Google Cloud Pub/Sub)" -ForegroundColor White
Write-Host "   - Swagger UI: http://localhost:8080/swagger-ui.html" -ForegroundColor White
Write-Host "   - Health Check: http://localhost:8080/api/v1/clientes/health" -ForegroundColor White
Write-Host ""

# Verificar se o Maven está disponível
try {
    $mvnVersion = mvn -version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Maven encontrado!" -ForegroundColor Green
    } else {
        throw "Maven não encontrado"
    }
} catch {
    Write-Host "❌ Maven não encontrado!" -ForegroundColor Red
    Write-Host "📝 Instale o Maven e adicione ao PATH:" -ForegroundColor Yellow
    Write-Host "   https://maven.apache.org/download.cgi" -ForegroundColor Cyan
    Write-Host "   Ou use: winget install Apache.Maven" -ForegroundColor Cyan
    exit 1
}

# Compilar o projeto
Write-Host "🔨 Compilando o projeto..." -ForegroundColor Yellow
mvn clean compile -q

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Compilação bem-sucedida!" -ForegroundColor Green
    Write-Host ""
    Write-Host "🌐 Iniciando aplicação..." -ForegroundColor Yellow
    Write-Host "📝 Para testar, use o arquivo: test-request.json" -ForegroundColor White
    Write-Host "🔗 Swagger UI: http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
    Write-Host ""
    
    # Iniciar com perfil de desenvolvimento
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
} else {
    Write-Host "❌ Erro na compilação!" -ForegroundColor Red
    exit 1
} 
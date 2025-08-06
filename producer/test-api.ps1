Write-Host "🧪 Testando API Ebury Cadastro Cliente..." -ForegroundColor Green
Write-Host ""

# Testar health check
Write-Host "🔍 Testando Health Check..." -ForegroundColor Yellow
try {
    $healthResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/clientes/health" -Method GET
    Write-Host $healthResponse -ForegroundColor Green
} catch {
    Write-Host "❌ Erro no Health Check: $($_.Exception.Message)" -ForegroundColor Red
}
Write-Host ""
Write-Host ""

# Testar cadastro de cliente
Write-Host "📝 Testando Cadastro de Cliente..." -ForegroundColor Yellow
Write-Host "📋 Enviando dados do arquivo test-request.json..." -ForegroundColor White
Write-Host ""

try {
    $jsonContent = Get-Content -Path "test-request.json" -Raw
    $headers = @{
        "Content-Type" = "application/json"
    }
    
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/v1/clientes/cadastro" -Method POST -Body $jsonContent -Headers $headers
    
    Write-Host "📤 Resposta da API:" -ForegroundColor Yellow
    $response | ConvertTo-Json -Depth 10 | Write-Host -ForegroundColor Green
    
} catch {
    Write-Host "❌ Erro no Cadastro: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $errorResponse = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($errorResponse)
        $errorBody = $reader.ReadToEnd()
        Write-Host "📋 Detalhes do erro:" -ForegroundColor Red
        Write-Host $errorBody -ForegroundColor Red
    }
}
Write-Host ""

Write-Host "✅ Teste concluído!" -ForegroundColor Green
Write-Host "🔗 Swagger UI: http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan 
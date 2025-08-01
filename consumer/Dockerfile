# Multi-stage build para otimizar o tamanho da imagem
FROM maven:3.9.5-openjdk-21 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de dependências primeiro (para aproveitar cache do Docker)
COPY pom.xml .
COPY src ./src

# Baixar dependências e compilar
RUN mvn clean package -DskipTests

# Imagem de produção
FROM openjdk:21-jre-slim

# Criar usuário não-root
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Definir diretório de trabalho
WORKDIR /app

# Copiar JAR da etapa de build
COPY --from=build /app/target/cadastro-cliente-consumer-*.jar app.jar

# Criar diretório para credenciais do GCP
RUN mkdir -p /etc/gcp && chown -R appuser:appuser /etc/gcp

# Mudar para usuário não-root
USER appuser

# Expor porta
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"] 
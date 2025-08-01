package com.ebury.cadastrocliente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ebury Cadastro Cliente Producer API")
                        .description("API para cadastro de clientes com integração Google Cloud Pub/Sub")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ebury Team")
                                .email("suporte@ebury.com")
                                .url("https://www.ebury.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://producer.cadastro-cliente.local")
                                .description("Servidor de Produção")
                ));
    }
} 
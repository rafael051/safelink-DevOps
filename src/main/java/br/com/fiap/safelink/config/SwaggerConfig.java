package br.com.fiap.safelink.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 📘 Configuração da documentação Swagger (OpenAPI 3) - SafeLink.
 * Inclui:
 * - Título e descrição da API
 * - Versão
 * - Contato e licença
 * - Esquema de autenticação JWT (bearer)
 * - Segurança aplicada globalmente aos endpoints
 * - URL base pública da API (Railway)
 */
@Configuration
public class SwaggerConfig {

    private static final String BEARER_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()

                // 🌍 URL base pública da API no Railway (ajuste se necessário)
                .addServersItem(new Server().url("http://localhost:8080"))

                // 📘 Informações da API
                .info(new Info()
                        .title("SafeLink API")
                        .description("API do projeto SafeLink — Sistema inteligente para alerta e prevenção de desastres naturais, desenvolvido como solução para o desafio Global Solution FIAP 2025.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe SafeLink - FIAP")
                                .email("safelink@fiap.com.br")
                                .url("https://github.com/rafael051/safelink"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT"))
                )

                // 🔐 Requer autenticação JWT globalmente
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME_NAME))

                // 🔐 Esquema de segurança JWT
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(BEARER_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                        )
                );
    }
}

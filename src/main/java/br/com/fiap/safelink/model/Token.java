package br.com.fiap.safelink.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 📦 DTO de resposta com o token JWT gerado.
 *
 * Retornado após autenticação bem-sucedida.
 */
@Schema(description = "Resposta contendo o token JWT gerado após login.")
public record Token(

        @Schema(description = "Token JWT válido para autenticação", example = "eyJhbGciOiJIUzI1NiIs...")
        String token,

        @Schema(description = "E-mail do usuário autenticado", example = "usuario@safelink.com")
        String email,

        @Schema(description = "Tempo de expiração do token em segundos", example = "14400")
        long expiresIn

) {}

package br.com.fiap.safelink.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 🔐 DTO para autenticação de usuários.
 *
 * Representa as credenciais fornecidas no login (/auth/login),
 * contendo e-mail e senha com validações e documentação Swagger.
 */
@Schema(description = "Credenciais fornecidas para autenticação do usuário.")
public record Credentials(

        @Schema(description = "E-mail do usuário", example = "admin@safelink.com")
        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail é obrigatório")
        String email,

        @Schema(description = "Senha do usuário", example = "admin123")
        @NotBlank(message = "Senha é obrigatória")
        String password

) {}

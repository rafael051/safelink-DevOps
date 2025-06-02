package br.com.fiap.safelink.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * # 🔐 Credenciais de Acesso
 *
 * Record usada para autenticação de usuários no sistema SafeLink.
 * Contém os dados essenciais: e-mail e senha.
 *
 * ---
 * ## 📌 Utilização
 * - DTO de entrada no endpoint de login (`/auth/login`)
 * - Usado para gerar o token JWT após validação
 */
public record Credentials(

        /** E-mail do usuário (obrigatório e válido). */
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        /** Senha do usuário (obrigatória). */
        @NotBlank(message = "A senha é obrigatória.")
        String password

) {}

package br.com.fiap.safelink.dto.request;

import br.com.fiap.safelink.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * # 📥 DTO: UserRequestDTO
 *
 * Representa os dados enviados via API para criação ou atualização de um usuário no sistema SafeLink.
 * Inclui validações e anotação especial para ocultar a senha nas respostas JSON.
 *
 * ---
 * ## 📌 Utilização
 * - Requisições de cadastro de usuários (POST).
 * - Atualizações parciais ou completas (PUT/PATCH).
 *
 * ---
 * @author Rafael
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class UserRequestDTO {

    // ===========================
    // 📧 Credenciais
    // ===========================

    /**
     * Endereço de e-mail do usuário.
     * Utilizado como login no sistema.
     */
    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    private String email;

    /**
     * Senha do usuário.
     * Será armazenada criptografada e não será retornada nas respostas.
     */
    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // ===========================
    // 🎭 Papel no Sistema
    // ===========================

    /**
     * Papel do usuário na aplicação.
     * Define o nível de acesso (ex: ADMIN, USER).
     */
    @NotNull(message = "O papel do usuário é obrigatório.")
    private UserRole role;
}

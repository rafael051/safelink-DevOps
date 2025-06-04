package br.com.fiap.safelink.dto.request;

import br.com.fiap.safelink.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "📥 Dados para criação ou atualização de usuário.")
public class UserRequestDTO {

    // ===========================
    // 📧 Credenciais
    // ===========================

    @Schema(description = "Endereço de e-mail do usuário. Utilizado como login.",
            example = "usuario@safelink.com", required = true)
    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    private String email;

    @Schema(description = "Senha do usuário. Não será exibida nas respostas.",
            example = "s3nh@F0rte", minLength = 6, required = true)
    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // ===========================
    // 🎭 Papel no Sistema
    // ===========================

    @Schema(description = "Papel do usuário na aplicação (ex: ADMIN, USER).",
            example = "ADMIN", required = true)
    @NotNull(message = "O papel do usuário é obrigatório.")
    private UserRole role;
}

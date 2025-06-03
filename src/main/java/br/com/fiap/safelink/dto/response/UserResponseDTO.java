package br.com.fiap.safelink.dto.response;

import br.com.fiap.safelink.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * # 📤 DTO: UserResponseDTO
 *
 * Representa os dados retornados pela API ao consultar ou listar usuários do sistema SafeLink.
 * Este DTO **não expõe a senha**, sendo seguro para uso em telas administrativas, dashboards e integrações.
 *
 * ---
 * ## 📌 Utilização
 * - Listagem de usuários (GET /usuarios)
 * - Consulta individual (GET /usuarios/{id})
 * - Retorno após criação ou edição de usuários
 *
 * ---
 * @author Rafael
 * @since 1.1
 */
@Data
@NoArgsConstructor
public class UserResponseDTO {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /**
     * Identificador único do usuário.
     */
    @Schema(description = "ID único do usuário", example = "42")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    // ===========================
    // 📧 Credenciais Públicas
    // ===========================

    /**
     * Endereço de e-mail do usuário.
     */
    @Schema(description = "E-mail do usuário (usado como login)", example = "joana.silva@exemplo.com")
    private String email;

    // ===========================
    // 🎭 Papel no Sistema
    // ===========================

    /**
     * Papel de acesso do usuário no sistema.
     */
    @Schema(description = "Role/perfil do usuário no sistema", example = "ADMIN")
    private UserRole role;
}

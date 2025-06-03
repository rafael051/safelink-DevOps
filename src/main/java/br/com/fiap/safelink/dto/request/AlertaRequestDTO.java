package br.com.fiap.safelink.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📦 DTO: AlertaRequestDTO
 *
 * Representa os dados recebidos via API para criação ou atualização de um alerta de risco.
 * Contém validações para garantir integridade dos dados e anotações para geração automática
 * de documentação com Swagger/OpenAPI.
 *
 * ---
 * ## 🧾 Campos Obrigatórios
 * - tipo
 * - nível de risco
 * - mensagem
 * - data de emissão
 * - ID da região associada
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaRequestDTO {

    // ===========================
    // 📝 Dados do Alerta
    // ===========================

    /**
     * Tipo textual do alerta de risco.
     * Ex: Enchente, Deslizamento, Tempestade, etc.
     */
    @Schema(example = "Enchente", description = "Tipo textual do alerta de risco")
    @NotBlank(message = "O tipo do alerta é obrigatório.")
    private String tipo;

    /**
     * Nível de risco associado ao alerta.
     * Ex: ALTO, MÉDIO, BAIXO.
     */
    @Schema(example = "ALTO", description = "Nível de risco do alerta")
    @NotBlank(message = "O nível de risco é obrigatório.")
    private String nivelRisco;

    /**
     * Mensagem explicativa direcionada aos moradores da região afetada.
     * Ex: "Evacuar imediatamente a área afetada pela enchente."
     */
    @Schema(example = "Evacuar imediatamente a área afetada pela enchente", description = "Mensagem informativa do alerta")
    @NotBlank(message = "A mensagem do alerta é obrigatória.")
    private String mensagem;

    /**
     * Data e hora da emissão do alerta.
     * Deve ser informada no formato dd/MM/yyyy HH:mm:ss.
     */
    @Schema(
            example = "03/06/2025 15:30:00",
            description = "Data e hora da emissão do alerta (formato dd/MM/yyyy HH:mm:ss)"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull(message = "A data de emissão do alerta é obrigatória.")
    private LocalDateTime emitidoEm;

    // ===========================
    // 🔗 Relacionamento
    // ===========================

    /**
     * Identificador da região associada ao alerta.
     * Deve referenciar uma região válida cadastrada no sistema.
     */
    @Schema(example = "1", description = "ID da região geográfica afetada pelo alerta")
    @NotNull(message = "O ID da região é obrigatório.")
    private Long idRegiao;
}

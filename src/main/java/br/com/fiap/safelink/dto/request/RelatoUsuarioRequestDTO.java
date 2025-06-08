package br.com.fiap.safelink.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📦 DTO: RelatoUsuarioRequestDTO
 *
 * Representa os dados enviados por cidadãos ou agentes de campo para registrar relatos
 * sobre eventos de risco ou condições observadas em regiões monitoradas pelo sistema SafeLink.
 *
 * ---
 * ## 📌 Utilização
 * - Utilizado em requisições POST para cadastrar um novo relato.
 * - Deve conter o ID do usuário que realizou o relato (campo obrigatório).
 *
 * ---
 * @author Rafael
 * @version 1.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatoUsuarioRequestDTO {

    // ===========================
    // 📝 Conteúdo do Relato
    // ===========================

    @Schema(
            example = "Há deslizamento parcial na encosta próxima à escola municipal.",
            description = "Mensagem relatando a situação observada pelo cidadão ou agente"
    )
    @NotBlank(message = "A mensagem do relato é obrigatória.")
    private String mensagem;

    @Schema(
            example = "03/06/2025 10:45:00",
            description = "Data e hora em que o relato foi feito (formato dd/MM/yyyy HH:mm:ss)"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull(message = "A data do relato é obrigatória.")
    private LocalDateTime dataRelato;

    // ===========================
    // 👤 Identificação do Usuário
    // ===========================

    @Schema(example = "1", description = "ID do usuário (cidadão ou agente) que realizou o relato")
    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    // ===========================
    // 📍 Localização do Relato
    // ===========================

    @Schema(example = "1", description = "ID da região geográfica associada ao relato")
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;


}

package br.com.fiap.safelink.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📦 DTO: PrevisaoRiscoRequestDTO
 *
 * Representa os dados de entrada para o cadastro ou atualização de uma previsão de risco
 * em uma determinada região monitorada pelo sistema SafeLink.
 *
 * ---
 * ## 📌 Utilização
 * - Usado em requisições POST e PUT.
 * - Alimenta modelos preditivos e permite ações preventivas.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrevisaoRiscoRequestDTO {

    // ===========================
    // ⚠️ Detalhes da Previsão
    // ===========================

    /**
     * Nível previsto de risco.
     * Ex: ALTO, MÉDIO, BAIXO, CRÍTICO.
     */
    @Schema(example = "MÉDIO", description = "Nível previsto de risco da região")
    @NotBlank(message = "O nível previsto é obrigatório.")
    private String nivelPrevisto;

    /**
     * Fonte da previsão.
     * Pode ser um serviço meteorológico ou sistema automatizado (ex: IA, INMET, ClimaTempo).
     */
    @Schema(example = "INMET", description = "Fonte de onde a previsão foi gerada")
    private String fonte;

    /**
     * Data e hora em que a previsão foi gerada.
     * Deve ser enviada no formato dd/MM/yyyy HH:mm:ss.
     */
    @Schema(
            example = "03/06/2025 09:00:00",
            description = "Data e hora da geração da previsão (formato dd/MM/yyyy HH:mm:ss)"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull(message = "A data de geração é obrigatória.")
    private LocalDateTime geradoEm;

    // ===========================
    // 📍 Localização
    // ===========================

    /**
     * Identificador da região à qual a previsão se refere.
     * Deve estar previamente cadastrada no sistema.
     */
    @Schema(example = "1", description = "ID da região para a qual a previsão foi gerada")
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;
}

package br.com.fiap.safelink.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * # 📤 DTO: AlertaResponseDTO
 *
 * Representa os dados enviados ao cliente ao consultar ou cadastrar um alerta.
 * Inclui os atributos principais do alerta e informações básicas da região associada.
 *
 * ---
 * ## 🧾 Utilização
 * - Retornado em respostas de endpoints GET e POST de alerta.
 * - Permite visualização imediata do alerta emitido e da região correspondente.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertaResponseDTO {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único do alerta. */
    private Long id;

    // ===========================
    // 📝 Detalhes do Alerta
    // ===========================

    /** Tipo textual do alerta (ex: Enchente, Deslizamento, Tempestade, etc). */
    private String tipo;

    /** Nível de risco do alerta (ex: ALTO, MÉDIO, BAIXO). */
    private String nivelRisco;

    /** Mensagem explicativa enviada à população. */
    private String mensagem;

    /** Data e hora em que o alerta foi emitido. */
    private LocalDateTime emitidoEm;

    // ===========================
    // 🌍 Dados da Região
    // ===========================

    /** ID da região geográfica associada ao alerta. */
    private Long regiaoId;

    /** Nome da região associada ao alerta. */
    private String regiaoNome;
}

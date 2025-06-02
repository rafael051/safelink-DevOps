package br.com.fiap.safelink.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📤 DTO: AlertaResponseDTO
 *
 * Representa os dados retornados ao cliente ao consultar ou criar um alerta.
 * Inclui detalhes do alerta e da região associada.
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
    // 📝 Dados do Alerta
    // ===========================

    /** Tipo do alerta (ex: Enchente, Deslizamento, Tempestade). */
    private String tipo;

    /** Nível de risco do alerta (ALTO, MÉDIO, BAIXO). */
    private String nivelRisco;

    /** Mensagem exibida aos usuários. */
    private String mensagem;

    /** Data e hora em que o alerta foi emitido. */
    private LocalDateTime emitidoEm;

    // ===========================
    // 🌍 Informações da Região
    // ===========================

    /** ID da região associada ao alerta. */
    private Long regiaoId;

    /** Nome da região associada ao alerta. */
    private String regiaoNome;
}

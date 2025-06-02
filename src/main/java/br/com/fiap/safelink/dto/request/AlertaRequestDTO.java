package br.com.fiap.safelink.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📨 DTO: AlertaRequestDTO
 *
 * Objeto de transferência para criar um novo Alerta.
 * Recebe os dados enviados pelo cliente (POST/PUT) de forma validada.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertaRequestDTO {

    // ===========================
    // 📝 Dados do Alerta
    // ===========================

    /** Tipo do alerta (ex: Enchente, Deslizamento, Tempestade). */
    @NotBlank(message = "O tipo do alerta é obrigatório.")
    private String tipo;

    /** Nível de risco associado ao alerta (ex: ALTO, MÉDIO, BAIXO). */
    @NotBlank(message = "O nível de risco é obrigatório.")
    private String nivelRisco;

    /** Mensagem personalizada exibida para os usuários da região. */
    @NotBlank(message = "A mensagem do alerta é obrigatória.")
    private String mensagem;

    /** Data e hora em que o alerta foi emitido pelo sistema. */
    @NotNull(message = "A data e hora de emissão do alerta é obrigatória.")
    private LocalDateTime emitidoEm;

    // ===========================
    // 🔗 Referência à Região
    // ===========================

    /** ID da região relacionada ao alerta. */
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;
}

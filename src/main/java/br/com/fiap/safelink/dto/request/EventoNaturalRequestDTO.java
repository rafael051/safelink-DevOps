package br.com.fiap.safelink.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📦 DTO: EventoNaturalRequestDTO
 *
 * Representa os dados necessários para registrar uma nova ocorrência de evento natural
 * (ex: enchentes, deslizamentos, vendavais) em uma região específica do sistema SafeLink.
 *
 * ---
 * ## 📌 Utilização
 * - Usado para cadastro de eventos reais ocorridos.
 * - Fornece informações para análises históricas e futuras previsões de risco.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoNaturalRequestDTO {

    // ===========================
    // 🌪️ Dados do Evento
    // ===========================

    /**
     * Tipo do evento natural ocorrido.
     * Ex: Enchente, Deslizamento, Vendaval.
     */
    @Schema(example = "Deslizamento", description = "Tipo do evento natural ocorrido")
    @NotBlank(message = "O tipo do evento é obrigatório.")
    private String tipo;

    /**
     * Descrição adicional do evento natural.
     * Pode incluir detalhes como danos observados ou causas percebidas.
     */
    @Schema(example = "Deslizamento de terra após fortes chuvas", description = "Descrição adicional do evento")
    private String descricao;

    /**
     * Data e hora em que o evento aconteceu.
     * Deve ser fornecida no formato dd/MM/yyyy HH:mm:ss.
     */
    @Schema(
            example = "03/06/2025 08:30:00",
            description = "Data e hora da ocorrência do evento (formato dd/MM/yyyy HH:mm:ss)"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull(message = "A data e hora da ocorrência são obrigatórias.")
    private LocalDateTime dataOcorrencia;

    // ===========================
    // 📍 Localização
    // ===========================

    /**
     * Identificador da região onde o evento foi registrado.
     * Deve referenciar uma região existente no banco de dados.
     */
    @Schema(example = "1", description = "ID da região geográfica onde o evento foi registrado")
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;
}

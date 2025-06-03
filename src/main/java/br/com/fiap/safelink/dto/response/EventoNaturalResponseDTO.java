package br.com.fiap.safelink.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * # 📤 DTO: EventoNaturalResponseDTO
 *
 * Retorna os dados de um evento natural registrado no sistema SafeLink.
 * É utilizado como resposta nas chamadas GET (consulta) ou POST (criação).
 *
 * ---
 * ## 📌 Utilização
 * - Apresenta ao cliente os detalhes do evento natural registrado.
 * - Inclui informações básicas da região relacionada.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoNaturalResponseDTO {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único do evento natural registrado. */
    private Long id;

    // ===========================
    // 🌪️ Detalhes do Evento
    // ===========================

    /** Tipo do evento natural (ex: Enchente, Deslizamento, Vendaval). */
    private String tipo;

    /** Descrição adicional fornecida no momento do registro do evento. */
    private String descricao;

    /** Data e hora da ocorrência do evento. */
    private LocalDateTime dataOcorrencia;

    // ===========================
    // 🌍 Dados da Região
    // ===========================

    /** ID da região geográfica vinculada ao evento. */
    private Long regiaoId;

    /** Nome da região associada ao evento. */
    private String regiaoNome;
}

package br.com.fiap.safelink.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📤 DTO: EventoNaturalResponseDTO
 *
 * Retorna os dados de um evento natural registrado no sistema.
 * Exibido em chamadas GET ou após criação de novos registros.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoNaturalResponseDTO {

    /** Identificador único do evento. */
    private Long id;

    /** Tipo do evento ocorrido (ex: Enchente, Deslizamento). */
    private String tipo;

    /** Descrição adicional fornecida no momento do registro. */
    private String descricao;

    /** Data e hora da ocorrência registrada. */
    private LocalDateTime dataOcorrencia;

    /** ID da região associada. */
    private Long regiaoId;

    /** Nome da região associada. */
    private String regiaoNome;
}

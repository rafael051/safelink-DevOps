package br.com.fiap.safelink.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📨 DTO: EventoNaturalRequestDTO
 *
 * Objeto usado para registrar uma nova ocorrência de evento natural.
 * Recebido via chamadas POST da API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoNaturalRequestDTO {

    /** Tipo do evento (ex: Enchente, Deslizamento, Vendaval). */
    @NotBlank(message = "O tipo do evento é obrigatório.")
    private String tipo;

    /** Descrição adicional do evento ocorrido. */
    private String descricao;

    /** Data e hora em que o evento ocorreu. */
    @NotNull(message = "A data e hora da ocorrência são obrigatórias.")
    private LocalDateTime dataOcorrencia;

    /** ID da região onde o evento ocorreu. */
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;
}

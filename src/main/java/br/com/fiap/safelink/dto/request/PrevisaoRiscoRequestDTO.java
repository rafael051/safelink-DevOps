package br.com.fiap.safelink.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📨 DTO: PrevisaoRiscoRequestDTO
 *
 * Objeto de entrada usado para cadastrar uma nova previsão de risco no sistema.
 * Enviado via chamadas POST ou PUT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrevisaoRiscoRequestDTO {

    /** Nível previsto de risco (ex: ALTO, MÉDIO, BAIXO). */
    @NotBlank(message = "O nível previsto é obrigatório.")
    private String nivelPrevisto;

    /** Fonte da previsão (ex: IA, INMET, ClimaTempo). */
    private String fonte;

    /** Data e hora em que a previsão foi gerada. */
    @NotNull(message = "A data de geração é obrigatória.")
    private LocalDateTime geradoEm;

    /** ID da região associada à previsão. */
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;
}

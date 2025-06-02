package br.com.fiap.safelink.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📨 DTO: RelatoUsuarioRequestDTO
 *
 * Usado para criação ou atualização de relatos feitos por usuários sobre eventos de risco.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatoUsuarioRequestDTO {

    /** Texto livre do relato. Pode incluir observações de risco, danos ou ocorrências. */
    @NotBlank(message = "A mensagem do relato é obrigatória.")
    private String mensagem;

    /** Data e hora em que o relato foi feito. */
    @NotNull(message = "A data do relato é obrigatória.")
    private LocalDateTime dataRelato;

    /** ID do usuário autor do relato. */
    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    /** ID da região onde o relato foi observado. */
    @NotNull(message = "O ID da região é obrigatório.")
    private Long regiaoId;
}

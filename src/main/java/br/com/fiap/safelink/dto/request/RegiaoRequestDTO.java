package br.com.fiap.safelink.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * # 📨 DTO: RegiaoRequestDTO
 *
 * Objeto de entrada para cadastro ou atualização de uma região monitorada.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegiaoRequestDTO {

    /** Nome da região (ex: Centro, Zona Norte). */
    @NotBlank(message = "O nome da região é obrigatório.")
    private String nome;

    /** Cidade à qual a região pertence. */
    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    /** Estado (UF) da região. */
    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    /** Latitude da região monitorada. */
    @NotNull(message = "A latitude é obrigatória.")
    private Double latitude;

    /** Longitude da região monitorada. */
    @NotNull(message = "A longitude é obrigatória.")
    private Double longitude;
}

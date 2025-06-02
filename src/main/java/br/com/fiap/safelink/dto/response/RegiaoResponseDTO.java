package br.com.fiap.safelink.dto.response;

import lombok.*;

/**
 * # 📤 DTO: RegiaoResponseDTO
 *
 * Objeto de saída para exibição dos dados de uma região monitorada.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegiaoResponseDTO {

    /** Identificador único da região. */
    private Long id;

    /** Nome da região cadastrada. */
    private String nome;

    /** Cidade onde a região está localizada. */
    private String cidade;

    /** Estado da região (UF). */
    private String estado;

    /** Coordenada de latitude. */
    private Double latitude;

    /** Coordenada de longitude. */
    private Double longitude;
}

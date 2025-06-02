package br.com.fiap.safelink.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📤 DTO: PrevisaoRiscoResponseDTO
 *
 * Objeto retornado pela API ao consultar ou criar uma previsão de risco.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrevisaoRiscoResponseDTO {

    /** Identificador único da previsão. */
    private Long id;

    /** Nível de risco estimado (ex: ALTO, MÉDIO, BAIXO). */
    private String nivelPrevisto;

    /** Fonte ou origem dos dados utilizados na previsão. */
    private String fonte;

    /** Momento exato em que a previsão foi registrada. */
    private LocalDateTime geradoEm;

    /** ID da região relacionada. */
    private Long regiaoId;

    /** Nome da região para exibição no front-end. */
    private String regiaoNome;
}

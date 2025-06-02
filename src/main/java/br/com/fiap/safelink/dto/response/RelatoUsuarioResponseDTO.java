package br.com.fiap.safelink.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * # 📤 DTO: RelatoUsuarioResponseDTO
 *
 * Retorna os dados de um relato feito por um usuário.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatoUsuarioResponseDTO {

    /** Identificador único do relato. */
    private Long id;

    /** Texto enviado pelo usuário. */
    private String mensagem;

    /** Data e hora do relato. */
    private LocalDateTime dataRelato;

    /** Nome do usuário autor do relato. */
    private String nomeUsuario;

    /** Nome da região onde o relato foi feito. */
    private String nomeRegiao;
}

package br.com.fiap.safelink.dto.response;

import lombok.*;

/**
 * # 📤 DTO: RegiaoResponseDTO
 *
 * Objeto de saída retornado pela API ao consultar uma região monitorada.
 * Apresenta os dados geográficos e administrativos de uma determinada área sob monitoramento.
 *
 * ---
 * ## 📌 Utilização
 * - Usado em respostas de endpoints GET (listar, buscar por ID) e POST (após criação).
 * - Exibido em telas de gestão e visualização de regiões no front-end.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegiaoResponseDTO {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único da região cadastrada no sistema. */
    private Long id;

    // ===========================
    // 🗺️ Dados Administrativos
    // ===========================

    /** Nome da região (ex: Centro, Zona Leste). */
    private String nome;

    /** Nome da cidade onde a região está localizada. */
    private String cidade;

    /** Sigla do estado (UF) da região (ex: SP, RJ). */
    private String estado;

    // ===========================
    // 📍 Coordenadas Geográficas
    // ===========================

    /** Coordenada de latitude da região (graus decimais). */
    private Double latitude;

    /** Coordenada de longitude da região (graus decimais). */
    private Double longitude;
}

package br.com.fiap.safelink.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * # 📦 DTO: RegiaoRequestDTO
 *
 * Representa os dados necessários para cadastrar ou atualizar uma região monitorada pelo sistema SafeLink.
 * Inclui validações e metadados para documentação via Swagger/OpenAPI.
 *
 * ---
 * ## 📌 Utilização
 * - Usado em requisições POST e PUT.
 * - Permite o gerenciamento de regiões geográficas sob monitoramento.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegiaoRequestDTO {

    // ===========================
    // 🗺️ Identificação da Região
    // ===========================

    /**
     * Nome da região monitorada.
     * Ex: Centro, Zona Norte, Bairro Alto.
     */
    @Schema(example = "Zona Norte", description = "Nome identificador da região")
    @NotBlank(message = "O nome da região é obrigatório.")
    private String nome;

    /**
     * Nome da cidade onde a região está localizada.
     */
    @Schema(example = "São Paulo", description = "Cidade da região")
    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    /**
     * Sigla do estado (UF) da região.
     * Deve conter 2 letras (ex: SP, RJ, MG).
     */
    @Schema(example = "SP", description = "Sigla do estado (UF) da região")
    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    // ===========================
    // 📍 Coordenadas Geográficas
    // ===========================

    /**
     * Latitude geográfica da região.
     * Valor em graus decimais (ex: -23.5365).
     */
    @Schema(example = "-23.5365", description = "Latitude geográfica da região")
    @NotNull(message = "A latitude é obrigatória.")
    private Double latitude;

    /**
     * Longitude geográfica da região.
     * Valor em graus decimais (ex: -46.6333).
     */
    @Schema(example = "-46.6333", description = "Longitude geográfica da região")
    @NotNull(message = "A longitude é obrigatória.")
    private Double longitude;
}

package br.com.fiap.safelink.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * # 📤 DTO: PrevisaoRiscoResponseDTO
 *
 * Objeto retornado pela API ao consultar ou criar uma previsão de risco.
 * Inclui informações do risco estimado e dados resumidos da região associada.
 *
 * ---
 * ## 📌 Utilização
 * - Exibido nas respostas dos endpoints GET e POST de previsão.
 * - Permite exibição clara dos dados para usuários e dashboards.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrevisaoRiscoResponseDTO {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único da previsão de risco. */
    private Long id;

    // ===========================
    // ⚠️ Detalhes da Previsão
    // ===========================

    /** Nível de risco estimado (ex: ALTO, MÉDIO, BAIXO, CRÍTICO). */
    private String nivelPrevisto;

    /** Fonte ou origem dos dados utilizados na previsão (ex: IA, INMET). */
    private String fonte;

    /** Momento exato em que a previsão foi registrada no sistema. */
    private LocalDateTime geradoEm;

    // ===========================
    // 🌍 Dados da Região
    // ===========================

    /** ID da região geográfica vinculada à previsão. */
    private Long regiaoId;

    /** Nome da região associada à previsão. */
    private String regiaoNome;
}

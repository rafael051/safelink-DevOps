package br.com.fiap.safelink.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 📄 DTO de filtro para PrevisaoRisco.
 *
 * Permite aplicar filtros dinâmicos em buscas de previsões futuras de risco.
 * Contempla campos como nível de risco previsto, fonte da previsão, faixa de datas e região.
 *
 * ---
 * ✅ Todos os campos são opcionais
 * ✅ Compatível com Specification e paginação
 * ✅ toString() sobrescrito para uso em cache com segurança
 *
 * Exemplo de uso no controller:
 * {@code @ParameterObject PrevisaoRiscoFilter filter}
 *
 * @author Rafael
 * @since 1.0
 */
public record PrevisaoRiscoFilter(

        // 🚨 Nível de Risco Previsto

        /** Nível de risco previsto (ex: "ALTO", "MÉDIO", "BAIXO", "CRÍTICO"). */
        String nivelPrevisto,

        // 🔎 Fonte da Previsão

        /** Origem da previsão (ex: "INMET", "IA - Modelo SafeLink V2"). */
        String fonte,

        // 🗓️ Faixa de Datas (geradoEm)

        /** Data/hora mínima de geração da previsão. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataInicio,

        /** Data/hora máxima de geração da previsão. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataFim,

        // 🌍 Região

        /** ID da região associada à previsão. */
        Long regiaoId

) {
        @Override
        public String toString() {
                return "nivelPrevisto=" + nivelPrevisto +
                        ",fonte=" + fonte +
                        ",dataInicio=" + dataInicio +
                        ",dataFim=" + dataFim +
                        ",regiaoId=" + regiaoId;
        }
}

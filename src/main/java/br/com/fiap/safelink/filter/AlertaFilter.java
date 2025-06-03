package br.com.fiap.safelink.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 📄 DTO de filtro para Alerta.
 *
 * Permite aplicar filtros dinâmicos em buscas paginadas de alertas.
 * Contempla campos de tipo, nível de risco, data de emissão e região.
 *
 * Todos os campos são opcionais.
 *
 * Exemplo de uso na controller:
 * `@ParameterObject AlertaFilter filter`
 *
 * 💡 Importante: sobrescreve toString() para permitir uso seguro em cache.
 *
 * @author Rafael
 * @since 1.0
 */
public record AlertaFilter(

        // 🔖 Tipo do Alerta

        /** Tipo do alerta (ex: "Enchente", "Deslizamento"). */
        String tipo,

        // 🚨 Nível de Risco

        /** Nível de risco associado ao alerta (ex: "Alto", "Médio", "Baixo"). */
        String nivelRisco,

        // 🗓️ Período de Emissão

        /** Data/hora mínima de emissão do alerta. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataInicio,

        /** Data/hora máxima de emissão do alerta. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataFim,

        // 🌍 Região

        /** ID da região onde o alerta foi emitido. */
        Long regiaoId

) {
        @Override
        public String toString() {
                return "tipo=" + tipo +
                        ",nivelRisco=" + nivelRisco +
                        ",dataInicio=" + dataInicio +
                        ",dataFim=" + dataFim +
                        ",regiaoId=" + regiaoId;
        }
}

package br.com.fiap.safelink.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 📄 DTO de filtro para EventoNatural.
 *
 * Permite aplicar filtros dinâmicos em buscas paginadas de eventos naturais registrados.
 * Contempla campos como tipo de evento, faixa de datas e região associada.
 *
 * ---
 * ✅ Todos os campos são opcionais
 * ✅ Compatível com Specification para consultas flexíveis
 * ✅ Sobrescreve toString() para ser usado como chave em cache
 *
 * Exemplo de uso no controller:
 * {@code @ParameterObject EventoNaturalFilter filter}
 *
 * @author Rafael
 * @since 1.0
 */
public record EventoNaturalFilter(

        // 🔖 Tipo do Evento

        /** Tipo do evento (ex: "Enchente", "Deslizamento", "Vendaval"). */
        String tipo,

        // 🗓️ Período de Ocorrência

        /** Data/hora mínima de ocorrência do evento. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataInicio,

        /** Data/hora máxima de ocorrência do evento. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataFim,

        // 🌍 Região

        /** ID da região onde o evento ocorreu. */
        Long regiaoId

) {
        @Override
        public String toString() {
                return "tipo=" + tipo +
                        ",dataInicio=" + dataInicio +
                        ",dataFim=" + dataFim +
                        ",regiaoId=" + regiaoId;
        }
}

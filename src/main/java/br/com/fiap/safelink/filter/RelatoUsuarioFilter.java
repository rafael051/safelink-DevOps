package br.com.fiap.safelink.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 📄 DTO de filtro para RelatoUsuario.
 *
 * Permite aplicar filtros dinâmicos em buscas por mensagem, datas, região e usuário.
 * Ideal para rastrear relatos específicos e cruzar com informações de risco.
 *
 * ---
 * ✅ Todos os campos são opcionais
 * ✅ Suporte para Specification + cache com chave dinâmica
 * ✅ Compatível com @ParameterObject em controllers
 *
 * @author Rafael
 * @since 1.0
 */
public record RelatoUsuarioFilter(

        // 💬 Texto do Relato

        /** Trecho do conteúdo textual enviado no relato. */
        String mensagem,

        // 🗓️ Período do Relato

        /** Data/hora mínima em que o relato foi feito. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataInicio,

        /** Data/hora máxima em que o relato foi feito. */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime dataFim,

        // 🧍 Usuário

        /** ID do usuário que enviou o relato. */
        Long usuarioId,

        // 🌍 Região

        /** ID da região associada ao relato. */
        Long regiaoId

) {
        @Override
        public String toString() {
                return "mensagem=" + mensagem +
                        ",dataInicio=" + dataInicio +
                        ",dataFim=" + dataFim +
                        ",usuarioId=" + usuarioId +
                        ",regiaoId=" + regiaoId;
        }
}

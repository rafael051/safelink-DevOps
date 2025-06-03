package br.com.fiap.safelink.filter;

/**
 * 📄 DTO de filtro para Região.
 *
 * Permite aplicar filtros dinâmicos em buscas por nome, cidade ou estado.
 * Útil para localizar regiões específicas ou realizar agrupamentos geográficos.
 *
 * ---
 * ✅ Todos os campos são opcionais
 * ✅ Suporte completo para Specification
 * ✅ toString() sobrescrito para uso seguro com cache
 *
 * Exemplo de uso:
 * {@code @ParameterObject RegiaoFilter filter}
 *
 * @author Rafael
 * @since 1.0
 */
public record RegiaoFilter(

        // 🗺️ Nome da Região

        /** Nome da região (ex: "Centro", "Zona Leste"). */
        String nome,

        // 🏙️ Cidade

        /** Cidade onde a região está localizada. */
        String cidade,

        // 🗾 Estado

        /** Sigla do estado (UF), como "SP", "RJ", "MG". */
        String estado

) {
    @Override
    public String toString() {
        return "nome=" + nome +
                ",cidade=" + cidade +
                ",estado=" + estado;
    }
}

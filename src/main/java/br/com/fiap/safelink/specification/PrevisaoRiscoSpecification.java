package br.com.fiap.safelink.specification;

import br.com.fiap.safelink.filter.PrevisaoRiscoFilter;
import br.com.fiap.safelink.model.PrevisaoRisco;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 📄 Specification dinâmica para consulta avançada de Previsões de Risco.
 *
 * Permite filtros combinados por nível previsto, fonte, datas e região.
 * Alinhado com todos os campos do {@link PrevisaoRiscoFilter}.
 * Ideal para buscas estratégicas, análises e visualizações antecipadas.
 *
 * @author Rafael
 * @since 1.0
 */
public class PrevisaoRiscoSpecification {

    public static Specification<PrevisaoRisco> withFilters(PrevisaoRiscoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🚨 Nível de Risco Previsto
            if (filter.nivelPrevisto() != null && !filter.nivelPrevisto().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("nivelPrevisto")), filter.nivelPrevisto().toLowerCase()));
            }

            // 🔎 Fonte da Previsão
            if (filter.fonte() != null && !filter.fonte().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("fonte")), "%" + filter.fonte().toLowerCase() + "%"));
            }

            // 🗓️ Faixa de Datas (geradoEm)
            if (filter.dataInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("geradoEm"), filter.dataInicio()));
            }
            if (filter.dataFim() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("geradoEm"), filter.dataFim()));
            }

            // 🌍 Região Associada
            if (filter.regiaoId() != null) {
                predicates.add(cb.equal(root.get("regiao").get("id"), filter.regiaoId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package br.com.fiap.safelink.specification;

import br.com.fiap.safelink.filter.AlertaFilter;
import br.com.fiap.safelink.model.Alerta;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 📄 Specification dinâmica para consulta avançada de Alertas.
 *
 * Permite filtros combinados por tipo, nível de risco, datas e região.
 * Alinhado com todos os campos do {@link AlertaFilter}.
 * Ideal para buscas flexíveis e analíticas na API SafeLink.
 *
 * @author Rafael
 * @since 1.0
 */
public class AlertaSpecification {

    public static Specification<Alerta> withFilters(AlertaFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔖 Tipo do Alerta
            if (filter.tipo() != null && !filter.tipo().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("tipo")), "%" + filter.tipo().toLowerCase() + "%"));
            }

            // 🚨 Nível de Risco
            if (filter.nivelRisco() != null && !filter.nivelRisco().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("nivelRisco")), filter.nivelRisco().toLowerCase()));
            }

            // 🗓️ Faixa de Datas (emitidoEm)
            if (filter.dataInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("emitidoEm"), filter.dataInicio()));
            }
            if (filter.dataFim() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("emitidoEm"), filter.dataFim()));
            }

            // 🌍 Região do Alerta
            if (filter.regiaoId() != null) {
                predicates.add(cb.equal(root.get("regiao").get("id"), filter.regiaoId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

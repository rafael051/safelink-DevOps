package br.com.fiap.safelink.specification;

import br.com.fiap.safelink.filter.EventoNaturalFilter;
import br.com.fiap.safelink.model.EventoNatural;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 📄 Specification dinâmica para consulta avançada de Eventos Naturais.
 *
 * Permite filtros combinados por tipo, data de ocorrência e região.
 * Alinhado com todos os campos do {@link EventoNaturalFilter}.
 * Ideal para buscas históricas, relatórios e visualizações analíticas.
 *
 * @author Rafael
 * @since 1.0
 */
public class EventoNaturalSpecification {

    public static Specification<EventoNatural> withFilters(EventoNaturalFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔖 Tipo do Evento
            if (filter.tipo() != null && !filter.tipo().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("tipo")), "%" + filter.tipo().toLowerCase() + "%"));
            }

            // 🗓️ Faixa de Datas (dataOcorrencia)
            if (filter.dataInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataOcorrencia"), filter.dataInicio()));
            }
            if (filter.dataFim() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataOcorrencia"), filter.dataFim()));
            }

            // 🌍 Região do Evento
            if (filter.regiaoId() != null) {
                predicates.add(cb.equal(root.get("regiao").get("id"), filter.regiaoId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package br.com.fiap.safelink.specification;

import br.com.fiap.safelink.filter.RegiaoFilter;
import br.com.fiap.safelink.model.Regiao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 📄 Specification dinâmica para consulta avançada de Regiões.
 *
 * Permite filtros combinados por nome, cidade e estado.
 * Alinhado com todos os campos do {@link RegiaoFilter}.
 * Ideal para localizar áreas monitoradas e fazer análises geográficas.
 *
 * @author Rafael
 * @since 1.0
 */
public class RegiaoSpecification {

    public static Specification<Regiao> withFilters(RegiaoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🗺️ Nome da Região
            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            // 🏙️ Cidade
            if (filter.cidade() != null && !filter.cidade().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("cidade")), "%" + filter.cidade().toLowerCase() + "%"));
            }

            // 🗾 Estado (UF)
            if (filter.estado() != null && !filter.estado().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("estado")), filter.estado().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

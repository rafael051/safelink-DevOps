package br.com.fiap.safelink.specification;

import br.com.fiap.safelink.filter.RelatoUsuarioFilter;
import br.com.fiap.safelink.model.RelatoUsuario;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 📄 Specification dinâmica para consulta avançada de Relatos de Usuário.
 *
 * Permite filtros combinados por mensagem, datas, região e usuário.
 * Alinhado com todos os campos do {@link RelatoUsuarioFilter}.
 * Ideal para análises comunitárias e detecção precoce de riscos.
 *
 * @author Rafael
 * @since 1.0
 */
public class RelatoUsuarioSpecification {

    public static Specification<RelatoUsuario> withFilters(RelatoUsuarioFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 💬 Texto da Mensagem
            if (filter.mensagem() != null && !filter.mensagem().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("mensagem")), "%" + filter.mensagem().toLowerCase() + "%"));
            }

            // 🗓️ Faixa de Datas (dataRelato)
            if (filter.dataInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataRelato"), filter.dataInicio()));
            }
            if (filter.dataFim() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataRelato"), filter.dataFim()));
            }

            // 🧍 Usuário
            if (filter.usuarioId() != null) {
                predicates.add(cb.equal(root.get("usuario").get("id"), filter.usuarioId()));
            }

            // 🌍 Região
            if (filter.regiaoId() != null) {
                predicates.add(cb.equal(root.get("regiao").get("id"), filter.regiaoId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

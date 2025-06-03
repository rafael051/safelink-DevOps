package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.PrevisaoRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 📁 Repositório JPA para a entidade {@link PrevisaoRisco}.
 *
 * Responsável por fornecer acesso às previsões de risco futuras armazenadas no sistema.
 * Suporta operações CRUD e consultas dinâmicas via Specification.
 *
 * ---
 * ### Funcionalidades oferecidas:
 * - 🔍 `findAll(Specification, Pageable)` — busca paginada com filtros dinâmicos.
 * - 🔎 `findAll(Specification)` — busca completa por critérios personalizados.
 * - ✅ `exists(Specification)` — verifica existência com base em filtros.
 * - #️⃣ `count(Specification)` — conta previsões com critérios aplicados.
 *
 * ---
 * @author Rafael
 * @since 1.0
 */
@Repository
public interface PrevisaoRiscoRepository extends
        JpaRepository<PrevisaoRisco, Long>,
        JpaSpecificationExecutor<PrevisaoRisco> {
}

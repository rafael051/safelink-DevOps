package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.Regiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 📁 Repositório JPA para a entidade {@link Regiao}.
 *
 * Responsável por fornecer acesso às regiões geográficas monitoradas pelo sistema.
 * Suporta operações CRUD e consultas dinâmicas com Specification.
 *
 * ---
 * ### Funcionalidades oferecidas:
 * - 🔍 `findAll(Specification, Pageable)` — busca paginada com filtros dinâmicos.
 * - 🔎 `findAll(Specification)` — lista completa com critérios personalizados.
 * - ✅ `exists(Specification)` — verifica existência com base em filtros.
 * - #️⃣ `count(Specification)` — conta registros com filtros aplicados.
 *
 * ---
 * @autor Rafael
 * @since 1.0
 */
@Repository
public interface RegiaoRepository extends
        JpaRepository<Regiao, Long>,
        JpaSpecificationExecutor<Regiao> {
}

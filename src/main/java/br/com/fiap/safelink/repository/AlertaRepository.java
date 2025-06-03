package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 📁 Repositório JPA para a entidade {@link Alerta}.
 *
 * Responsável por fornecer acesso aos alertas de risco emitidos pelo sistema.
 * Suporta operações CRUD padrão e consultas avançadas via Specification.
 *
 * ---
 * ### Funcionalidades oferecidas:
 * - 🔍 `findAll(Specification, Pageable)` — busca paginada com filtros dinâmicos.
 * - 🔎 `findAll(Specification)` — lista completa com critérios personalizados.
 * - ✅ `exists(Specification)` — verifica existência com base em filtros.
 * - #️⃣ `count(Specification)` — conta alertas com base em filtros aplicados.
 *
 * ---
 * @autor Rafael
 * @since 1.0
 */
@Repository
public interface AlertaRepository extends
        JpaRepository<Alerta, Long>,
        JpaSpecificationExecutor<Alerta> {
}

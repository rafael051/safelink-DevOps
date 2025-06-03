package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.EventoNatural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 📁 Repositório JPA para a entidade {@link EventoNatural}.
 *
 * Responsável por fornecer acesso aos dados de eventos naturais já registrados no sistema.
 * Suporta operações CRUD padrão e consultas avançadas via Specification.
 *
 * ---
 * ### Funcionalidades oferecidas:
 * - 🔍 `findAll(Specification, Pageable)` — busca paginada com filtros dinâmicos.
 * - 🔎 `findAll(Specification)` — busca lista completa com critérios.
 * - ✅ `exists(Specification)` — verifica existência com base em filtros.
 * - #️⃣ `count(Specification)` — conta registros com base em filtros.
 *
 * ---
 * @author Rafael
 * @since 1.0
 */
@Repository
public interface EventoNaturalRepository extends
        JpaRepository<EventoNatural, Long>,
        JpaSpecificationExecutor<EventoNatural> {
}

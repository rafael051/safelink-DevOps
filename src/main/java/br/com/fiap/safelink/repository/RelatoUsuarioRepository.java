package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.RelatoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 📁 Repositório JPA para a entidade {@link RelatoUsuario}.
 *
 * Responsável por fornecer acesso aos relatos enviados por usuários do sistema.
 * Suporta operações CRUD e consultas avançadas com Specification.
 *
 * ---
 * ### Funcionalidades oferecidas:
 * - 🔍 `findAll(Specification, Pageable)` — busca paginada com filtros dinâmicos.
 * - 🔎 `findAll(Specification)` — busca completa com critérios específicos.
 * - ✅ `exists(Specification)` — verifica existência com base em filtros.
 * - #️⃣ `count(Specification)` — conta relatos com base em critérios.
 *
 * ---
 * @author Rafael
 * @since 1.0
 */
@Repository
public interface RelatoUsuarioRepository extends
        JpaRepository<RelatoUsuario, Long>,
        JpaSpecificationExecutor<RelatoUsuario> {
}

package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.RelatoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 📁 Repositório JPA para a entidade RelatoUsuario.
 *
 * Permite operações CRUD automáticas utilizando Spring Data JPA.
 * Também oferece suporte a Specification para consultas com filtros dinâmicos.
 *
 * ---
 * - findAll(Specification, Pageable)
 * - findAll(Specification)
 * - exists(Specification)
 * - count(Specification)
 *
 * @author Rafael
 * @since 1.0
 */
@Repository
public interface RelatoUsuarioRepository
        extends JpaRepository<RelatoUsuario, Long>,
        JpaSpecificationExecutor<RelatoUsuario> {

}

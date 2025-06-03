package br.com.fiap.safelink.repository;

import br.com.fiap.safelink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 📁 Repositório JPA para a entidade User.
 *
 * Permite operações CRUD automáticas utilizando Spring Data JPA.
 * Inclui métodos auxiliares para autenticação e verificação de existência por e-mail.
 *
 * ---
 * - findByEmail(String)
 * - existsByEmail(String)
 *
 * Ideal para uso com autenticação via Spring Security e validação de usuários.
 *
 * @author Rafael
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 🔍 Busca um usuário pelo e-mail (usado no login).
     *
     * @param email e-mail do usuário
     * @return usuário correspondente, se existir
     */
    Optional<User> findByEmail(String email);

    /**
     * ✅ Verifica se já existe um usuário com o e-mail informado.
     *
     * @param email e-mail a ser verificado
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);
}

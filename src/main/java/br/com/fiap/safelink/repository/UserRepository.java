package br.com.fiap.safelink.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.safelink.model.User;

/**
 * # 🔍 Repositório: UserRepository
 *
 * Interface de persistência da entidade `User`.
 * Fornece métodos para manipulação de dados dos usuários no banco.
 *
 * ---
 * ## 📌 Métodos customizados
 * - `findByEmail(String username)` → busca um usuário pelo nome de login (e-mail)
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 🔎 Busca um usuário pelo nome de login (e-mail).
     *
     * @param username e-mail informado como credencial de login
     * @return um Optional com o User correspondente (ou vazio se não encontrado)
     */
    Optional<User> findByEmail(String username);

}

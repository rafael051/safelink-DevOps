package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ❌ Exceção: UsuarioNotFoundException
 *
 * Lançada quando um usuário não é encontrado no banco de dados.
 */
public class UsuarioNotFoundException extends ResponseStatusException {

    /**
     * Construtor padrão com ID do usuário.
     *
     * @param id ID do usuário não encontrado
     */
    public UsuarioNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Usuário não encontrado: ID " + id);
    }

    /**
     * Construtor alternativo com mensagem customizada.
     *
     * @param message mensagem explicativa
     */
    public UsuarioNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

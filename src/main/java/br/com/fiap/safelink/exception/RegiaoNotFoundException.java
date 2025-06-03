package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ❌ Exceção lançada quando uma Região não é encontrada.
 *
 * Utilizada em operações que exigem a existência prévia do registro de região.
 *
 * ---
 * Pode ocorrer nas operações de:
 * - Consulta por ID
 * - Atualização
 * - Remoção
 *
 * @author Rafael
 * @since 1.0
 */
public class RegiaoNotFoundException extends ResponseStatusException {

    /**
     * Construtor padrão com ID ausente.
     *
     * @param id identificador da região
     */
    public RegiaoNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Região não encontrada para o ID: " + id);
    }

    /**
     * Construtor com mensagem customizada.
     *
     * @param message mensagem explicativa
     */
    public RegiaoNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

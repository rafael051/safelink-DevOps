package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ❌ Exceção lançada quando um Alerta não é encontrado.
 *
 * Utilizada em operações que exigem a existência prévia do registro de alerta.
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
public class AlertaNotFoundException extends ResponseStatusException {

    /**
     * Construtor padrão com ID ausente.
     *
     * @param id identificador do alerta
     */
    public AlertaNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Alerta não encontrado para o ID: " + id);
    }

    /**
     * Construtor com mensagem customizada.
     *
     * @param message mensagem explicativa
     */
    public AlertaNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

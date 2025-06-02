package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ❌ Exceção lançada quando um alerta não é encontrado no banco de dados.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlertaNotFoundException extends RuntimeException {

    public AlertaNotFoundException(Long id) {
        super("Alerta não encontrado com ID: " + id);
    }

    public AlertaNotFoundException(String message) {
        super(message);
    }
}

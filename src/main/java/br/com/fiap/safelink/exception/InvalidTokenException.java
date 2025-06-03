package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ⚠️ Exceção lançada quando um token JWT é inválido ou malformado.
 *
 * Situações comuns:
 * - Token expirado
 * - Claims ausentes (como ID, e-mail ou role)
 * - Assinatura inválida
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {

    /**
     * Cria uma exceção de token inválido com mensagem customizada.
     *
     * @param message Detalhes do erro de validação do token
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}

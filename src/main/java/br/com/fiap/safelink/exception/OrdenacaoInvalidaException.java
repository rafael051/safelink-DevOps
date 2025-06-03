package br.com.fiap.safelink.exception;

/**
 * Exceção lançada quando o campo de ordenação solicitado não é permitido ou reconhecido.
 */
public class OrdenacaoInvalidaException extends RuntimeException {

    public OrdenacaoInvalidaException(String campo) {
        super("Campo de ordenação inválido: '" + campo + "'");
    }
}

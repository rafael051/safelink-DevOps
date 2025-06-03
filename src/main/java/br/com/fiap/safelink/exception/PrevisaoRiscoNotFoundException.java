package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ❌ Exceção lançada quando uma Previsão de Risco não é encontrada.
 *
 * Utilizada em operações que exigem a existência prévia do registro de previsão.
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
public class PrevisaoRiscoNotFoundException extends ResponseStatusException {

    /**
     * Construtor padrão com ID ausente.
     *
     * @param id identificador da previsão de risco
     */
    public PrevisaoRiscoNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Previsão de risco não encontrada para o ID: " + id);
    }

    /**
     * Construtor com mensagem customizada.
     *
     * @param message mensagem explicativa
     */
    public PrevisaoRiscoNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

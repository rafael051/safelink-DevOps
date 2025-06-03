package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ❌ Exceção lançada quando um Relato de Usuário não é encontrado.
 *
 * Utilizada em operações que exigem a existência prévia do registro de relato.
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
public class RelatoUsuarioNotFoundException extends ResponseStatusException {

    /**
     * Construtor padrão com ID ausente.
     *
     * @param id identificador do relato de usuário
     */
    public RelatoUsuarioNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Relato de usuário não encontrado para o ID: " + id);
    }

    /**
     * Construtor com mensagem customizada.
     *
     * @param message mensagem explicativa
     */
    public RelatoUsuarioNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

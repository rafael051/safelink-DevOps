package br.com.fiap.safelink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * ❌ Exceção lançada quando um Evento Natural não é encontrado.
 *
 * Utilizada em operações que exigem a existência prévia do registro.
 *
 * @author Rafael
 * @since 1.0
 */
public class EventoNaturalNotFoundException extends ResponseStatusException {

  /**
   * Construtor padrão com ID ausente.
   *
   * @param id identificador do evento natural
   */
  public EventoNaturalNotFoundException(Long id) {
    super(HttpStatus.NOT_FOUND, "Evento natural não encontrado para o ID: " + id);
  }

  /**
   * Construtor com mensagem customizada.
   *
   * @param message mensagem explicativa
   */
  public EventoNaturalNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}

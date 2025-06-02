package br.com.fiap.safelink.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ⚠️ Utilitário para exceções de autenticação.
 *
 * Centraliza lançamento de exceções e envio de respostas JSON relacionadas à autenticação.
 */
public final class AuthExceptionUtils {

    private AuthExceptionUtils() {
        // Utilitário estático - previne instanciamento
    }

    /**
     * 🔐 Lança uma exceção padrão de senha incorreta.
     *
     * @param email E-mail do usuário que falhou no login
     */
    public static void invalidPassword(String email) {
        throw new BadCredentialsException("Senha incorreta para: " + email);
    }

    /**
     * 📤 Envia uma resposta JSON com erro HTTP e mensagem personalizada.
     *
     * @param response objeto HttpServletResponse
     * @param status   código HTTP a ser retornado (ex: 401, 403)
     * @param message  mensagem de erro a ser exibida ao cliente
     * @throws IOException se ocorrer erro ao escrever a resposta
     */
    public static void sendJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format("""
            {
              "timestamp": "%s",
              "status": %d,
              "error": "%s"
            }
        """, LocalDateTime.now(), status, message));
    }
}

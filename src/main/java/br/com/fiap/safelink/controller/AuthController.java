package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.model.Credentials;
import br.com.fiap.safelink.model.Token;
import br.com.fiap.safelink.service.AuthService;
import br.com.fiap.safelink.service.TokenService;
import br.com.fiap.safelink.exception.AuthExceptionUtils;
import br.com.fiap.safelink.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * ## 🔐 AuthController
 *
 * Controlador responsável pela autenticação de usuários e geração de tokens JWT.
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "1 - Autenticação", description = "Endpoints para login e emissão de tokens JWT")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://<seu-projeto>.up.railway.app"
})

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    /**
     * ### 🎯 POST /login
     *
     * Autentica um usuário com base nas credenciais fornecidas (email e senha).
     * Retorna um token JWT se a autenticação for bem-sucedida.
     *
     * @param credentials objeto contendo `email` e `password`
     * @return token JWT para ser usado em requisições autenticadas
     *
     * @throws BadCredentialsException se a senha estiver incorreta
     */
    @PostMapping("/login")
    public Token login(@Valid @RequestBody Credentials credentials) {
        User user = (User) authService.loadUserByUsername(credentials.email());

        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            log.warn("❌ Senha incorreta para usuário: {}", credentials.email());
            AuthExceptionUtils.invalidPassword(credentials.email());
        }

        log.info("✅ Login bem-sucedido para: {}", credentials.email());
        return tokenService.createToken(user);
    }

}

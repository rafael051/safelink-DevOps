package br.com.fiap.safelink.service;

import br.com.fiap.safelink.exception.InvalidTokenException;
import br.com.fiap.safelink.model.Token;
import br.com.fiap.safelink.model.User;
import br.com.fiap.safelink.model.enums.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * 📦 Serviço: TokenService
 *
 * Responsável pela geração e validação de tokens JWT no sistema SafeLink.
 */
@Service
@Slf4j
public class TokenService {

    private final Algorithm algorithm;

    @Value("${jwt.expiration-seconds:14400}") // padrão: 4 horas
    private long expirationSeconds;

    public TokenService(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    /**
     * 🔐 Gera um token JWT contendo ID, e-mail e role com prefixo 'ROLE_'
     *
     * @param user usuário autenticado
     * @return objeto Token com JWT assinado
     */
    public Token createToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expirationSeconds);

        String prefixedRole = "ROLE_" + user.getRole().name(); // ✅ solução do problema

        String jwt = JWT.create()
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiresAt))
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", prefixedRole) // ✅ com prefixo que o Spring espera
                .sign(algorithm);

        return new Token(jwt, user.getEmail(), expirationSeconds);
    }

    /**
     * 📥 Extrai as informações do usuário a partir de um token JWT válido.
     *
     * @param jwt token recebido no header
     * @return usuário autenticado extraído do JWT
     */
    public User getUserFromToken(String jwt) {
        try {
            var jwtVerified = JWT.require(algorithm).build().verify(jwt);

            String subject = jwtVerified.getSubject();
            String email = jwtVerified.getClaim("email").asString();
            String role = jwtVerified.getClaim("role").asString();

            if (subject == null || email == null || role == null) {
                throw new InvalidTokenException("Token JWT com campos obrigatórios ausentes.");
            }

            // ⚠️ Remove o prefixo ROLE_ para converter em enum UserRole
            UserRole parsedRole = UserRole.valueOf(role.replace("ROLE_", ""));

            return User.builder()
                    .id(Long.parseLong(subject))
                    .email(email)
                    .role(parsedRole)
                    .build();

        } catch (JWTVerificationException | IllegalArgumentException ex) {
            log.warn("❌ Token inválido: {}", ex.getMessage());
            throw new InvalidTokenException("Token inválido ou expirado.");
        }
    }
}

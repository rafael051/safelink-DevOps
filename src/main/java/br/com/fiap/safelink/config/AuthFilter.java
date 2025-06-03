package br.com.fiap.safelink.config;

import br.com.fiap.safelink.service.TokenService;
import br.com.fiap.safelink.exception.AuthExceptionUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * # 🔐 Filtro: AuthFilter (adaptado Allibus)
 *
 * Filtro de autenticação JWT com suporte a caminhos públicos.
 * Baseado na implementação do projeto Recebedoria.
 */
@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final AntPathMatcher matcher = new AntPathMatcher();

    private final TokenService tokenService;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/login", "/auth/login",
            "/swagger-ui/**", "/v3/api-docs/**"
    );

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        log.debug("🔎 Requisição para URI: {}", uri);

        if (isPublicPath(uri)) {
            log.debug("🔓 Acesso público permitido para: {}", uri);
            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(AUTH_HEADER);

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = header.substring(BEARER_PREFIX.length());

        try {
            var user = tokenService.getUserFromToken(jwt);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("✅ Autenticação JWT bem-sucedida para: {}", user.getEmail());
        } catch (Exception ex) {
            log.warn("⚠️ JWT inválido: {}", ex.getMessage());
            AuthExceptionUtils.sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATHS.stream().anyMatch(pattern -> matcher.match(pattern, uri));
    }
}

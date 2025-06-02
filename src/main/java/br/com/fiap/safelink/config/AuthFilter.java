package br.com.fiap.safelink.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.safelink.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * # 🔐 Filtro: AuthFilter
 *
 * Filtro responsável por interceptar todas as requisições HTTP e validar o token JWT presente no cabeçalho Authorization.
 *
 * ---
 * ## 🧩 Funcionalidades
 * - Extrai e valida o token JWT da requisição.
 * - Recupera o usuário autenticado e registra no contexto de segurança do Spring.
 * - Rejeita requisições sem "Bearer " ou sem token válido.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // ============================
        // 📥 Captura do Header
        // ============================
        var header = request.getHeader("Authorization");

        if (header == null || header.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // ============================
        // ❌ Validação do prefixo
        // ============================
        if (!header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                    "message": "Header deve iniciar com 'Bearer '"
                }
            """);
            return;
        }

        // ============================
        // 🔓 Extração e validação do token
        // ============================
        var jwt = header.replace("Bearer ", "");
        var user = tokenService.getUserFromToken(jwt);

        // ============================
        // ✅ Autenticação no contexto
        // ============================
        var authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 🟢 Continuação da requisição
        filterChain.doFilter(request, response);
    }
}

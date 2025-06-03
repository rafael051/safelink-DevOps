package br.com.fiap.safelink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 🔐 Configuração de segurança da aplicação SafeLink.
 *
 * Define as regras de autorização baseadas em roles, integração com filtro JWT e
 * política stateless para garantir segurança e escalabilidade.
 */
@Configuration
public class SecurityConfig {

    private final AuthFilter authFilter;

    public SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    /** 🔓 Endpoints públicos (acessíveis sem autenticação JWT) */
    private static final String[] PUBLIC_ENDPOINTS = {
            "/", "/login", "/auth/login",
            "/swagger-ui.html", "/swagger-ui/**",
            "/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs.yaml"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 🚫 Desabilita CSRF para APIs REST stateless
                .csrf(csrf -> csrf.disable())

                // 📦 Política stateless (não armazena sessão no servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔐 Regras de autorização por rota e método
                .authorizeHttpRequests(auth -> auth
                        // ✅ Endpoints públicos
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // 🔓 Cadastro e login permitidos para todos
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        // 🔍 Endpoints de leitura permitidos para USER e ADMIN
                        .requestMatchers(HttpMethod.GET,
                                "/alertas/**",
                                "/eventos-naturais/**",
                                "/regioes/**",
                                "/previsoes-risco/**",
                                "/relatos-usuario/**",
                                "/users/**"
                        ).hasAnyRole("USER", "ADMIN") // exige "ROLE_USER" ou "ROLE_ADMIN" no token

                        // ✏️ Escrita e alteração restritas a ADMIN
                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")   // requer "ROLE_ADMIN"
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                        // 🔐 Qualquer outra requisição precisa apenas estar autenticada
                        .anyRequest().authenticated()
                )

                // 🔗 Aplica o filtro JWT antes da autenticação padrão
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)

                // 🔧 Constrói e retorna a cadeia de filtros
                .build();
    }

    /** 🔑 Bean de codificação de senhas (BCrypt) */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

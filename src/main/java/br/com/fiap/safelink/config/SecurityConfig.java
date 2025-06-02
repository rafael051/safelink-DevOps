package br.com.fiap.safelink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * # 🔐 Configuração de Segurança
 *
 * Define as regras de autenticação e autorização da API SafeLink,
 * aplicando filtros personalizados e controlando o acesso às rotas.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    /**
     * 🔒 Define a cadeia de filtros de segurança para as requisições HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // 🧩 Acesso restrito à rota de transações (exemplo)
                        .requestMatchers("/transactions/**").hasRole("ADMIN")

                        // 🆓 Acesso público para cadastro de usuário
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()

                        // 🆓 Acesso público para autenticação (login)
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()

                        // 🔐 Demais rotas exigem autenticação
                        .anyRequest().authenticated())

                // ⚙️ Habilita autenticação básica (apenas como fallback)
                .httpBasic(Customizer.withDefaults())

                // 🔗 Insere o filtro JWT antes do filtro padrão do Spring
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)

                // 🚫 Desabilita CSRF para APIs REST (sem sessões)
                .csrf(csrf -> csrf.disable())

                .build();
    }

    /**
     * 🔑 Encoder de senhas com BCrypt (padrão recomendado).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

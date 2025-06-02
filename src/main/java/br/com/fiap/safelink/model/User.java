package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * # 📄 Entidade: User
 *
 * Representa um usuário autenticado no sistema SafeLink. Cada usuário possui um e-mail único,
 * senha criptografada e uma role (papel) que define seus níveis de acesso na aplicação.
 * Implementa a interface `UserDetails` do Spring Security para controle de autenticação.
 *
 * ---
 * ## 🔐 Segurança e Acesso
 * - Usuários são autenticados por `email` e `password`
 * - As permissões são derivadas do atributo `role`
 * - Compatível com Spring Security e JWT
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /**
     * Identificador único do usuário (chave primária).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===========================
    // 📧 Credenciais de Acesso
    // ===========================

    /**
     * E-mail do usuário. Usado como login.
     * Deve ser único e seguir o padrão de endereço de e-mail válido.
     */
    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Senha do usuário, que será armazenada já criptografada.
     * Deve ter pelo menos 8 caracteres.
     */
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String password;

    // ===========================
    // 🎭 Papel do Usuário
    // ===========================

    /**
     * Papel do usuário na aplicação.
     * Pode ser `ADMIN` ou `USER`.
     * Define os privilégios de acesso aos recursos protegidos.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // ===========================
    // 🛡️ Métodos da interface UserDetails
    // ===========================

    /**
     * Retorna a lista de autoridades (permissões) do usuário.
     * Cada `UserRole` é convertido para `GrantedAuthority`.
     *
     * @return Lista com a autoridade baseada na role.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Retorna o identificador principal do usuário usado para login.
     * No nosso caso, é o `email`.
     *
     * @return e-mail do usuário
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Define se a conta do usuário está expirada.
     * Aqui, sempre retorna `true` (conta nunca expira).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Define se a conta está bloqueada.
     * Aqui, sempre retorna `true` (conta nunca bloqueia).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Define se as credenciais estão expiradas.
     * Aqui, sempre retorna `true` (credencial sempre válida).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Define se o usuário está habilitado no sistema.
     * Aqui, sempre `true`. Pode ser ajustado futuramente.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

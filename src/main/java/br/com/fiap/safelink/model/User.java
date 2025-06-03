package br.com.fiap.safelink.model;

import br.com.fiap.safelink.model.enums.UserRole;
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

    /** Identificador único do usuário (chave primária). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    // ===========================
    // 📧 Credenciais de Acesso
    // ===========================

    /**
     * E-mail do usuário.
     * Usado como login no sistema.
     * Deve ser único e seguir o padrão de endereço de e-mail válido.
     */
    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Column(name = "ds_email", unique = true, nullable = false)
    private String email;

    /**
     * Senha do usuário, que será armazenada já criptografada.
     * Deve ter no mínimo 8 caracteres por segurança.
     */
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    @Column(name = "ds_senha", nullable = false)
    private String password;

    // ===========================
    // 🎭 Papel do Usuário
    // ===========================

    /**
     * Papel (role) do usuário na aplicação.
     * Pode ser ADMIN ou USER.
     * Define os privilégios de acesso aos recursos protegidos.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_role", nullable = false)
    private UserRole role;

    // ===========================
    // 🛡️ Métodos da interface UserDetails
    // ===========================

    /**
     * Retorna a lista de autoridades (permissões) do usuário.
     * Cada UserRole é convertido em uma autoridade do Spring Security.
     *
     * @return Lista com a autoridade baseada na role (com prefixo "ROLE_").
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Retorna o login do usuário.
     * Neste caso, o e-mail é usado como identificador principal.
     *
     * @return e-mail do usuário
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica se a conta está expirada.
     * Retorna true indicando que nunca expira.
     *
     * @return true (conta não expira)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se a conta está bloqueada.
     * Retorna true indicando que nunca está bloqueada.
     *
     * @return true (conta nunca bloqueia)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica se as credenciais estão expiradas.
     * Retorna true indicando que são sempre válidas.
     *
     * @return true (credenciais não expiram)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se o usuário está ativo/habilitado.
     * Retorna true (padrão), mas pode ser ajustado futuramente.
     *
     * @return true (usuário está habilitado)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

package br.com.fiap.safelink.config;

import br.com.fiap.safelink.model.enums.UserRole;
import br.com.fiap.safelink.model.User;
import br.com.fiap.safelink.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * # 🌱 DatabaseSeeder
 *
 * Inicializa o banco com dois usuários padrões:
 * - Um ADMIN e um USER com e-mail e senha criptografada.
 * Executado automaticamente ao iniciar a aplicação.
 *
 * ⚠️ Este seeder deve ser usado apenas em ambientes de desenvolvimento.
 */
@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedDatabase() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .email("admin@safelink.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build();

            User user = User.builder()
                    .email("user@safelink.com")
                    .password(passwordEncoder.encode("user12345"))
                    .role(UserRole.USER)
                    .build();

            userRepository.save(admin);
            userRepository.save(user);
        }
    }
}

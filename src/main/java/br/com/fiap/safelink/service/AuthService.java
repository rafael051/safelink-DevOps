package br.com.fiap.safelink.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.safelink.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor // Lombok gera o construtor com os campos finais
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carrega um usuário pelo e-mail (usado como username pelo Spring Security).
     *
     * @param email e-mail do usuário
     * @return UserDetails para o processo de autenticação
     * @throws UsernameNotFoundException se não encontrar o usuário
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Tentativa de login com e-mail inexistente: {}", email);
                    return new UsernameNotFoundException("Usuário com e-mail '" + email + "' não encontrado");
                });
    }

}

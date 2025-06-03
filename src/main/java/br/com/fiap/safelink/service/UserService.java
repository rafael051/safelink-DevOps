package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.UserRequestDTO;
import br.com.fiap.safelink.dto.response.UserResponseDTO;
import br.com.fiap.safelink.exception.UsuarioNotFoundException;
import br.com.fiap.safelink.model.User;
import br.com.fiap.safelink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * # 👤 Service: UserService
 *
 * Camada de regras de negócio para a entidade `User`.
 * Responsável por:
 * - Criação, leitura, atualização e exclusão de usuários
 * - Validações de unicidade de e-mail
 * - Conversões entre DTO e entidade
 * - Criptografia de senha
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    // ============================================
    // 🔗 Injeção de dependências
    // ============================================

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // ============================================
    // ➕ Criação
    // ============================================

    /**
     * Cria um novo usuário no sistema.
     */
    public UserResponseDTO criarUsuario(UserRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "E-mail já cadastrado.");
        }

        User user = modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user = repository.save(user);
        log.info("✅ Usuário criado com sucesso: ID {}", user.getId());
        return toDTO(user);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * Atualiza os dados de um usuário existente.
     */
    public Optional<UserResponseDTO> atualizarUsuario(Long id, UserRequestDTO dto) {
        return repository.findById(id).map(user -> {
            if (!user.getEmail().equals(dto.getEmail()) && repository.existsByEmail(dto.getEmail())) {
                throw new ResponseStatusException(BAD_REQUEST, "E-mail já está em uso por outro usuário.");
            }

            modelMapper.map(dto, user);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));

            user = repository.save(user);
            log.info("✏️ Usuário atualizado com sucesso: ID {}", user.getId());
            return toDTO(user);
        });
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * Lista todos os usuários cadastrados.
     */
    public List<UserResponseDTO> listarTodos() {
        log.info("📋 Listando todos os usuários.");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * Busca um usuário por ID.
     */
    public Optional<UserResponseDTO> buscarPorId(Long id) {
        log.info("🔍 Buscando usuário por ID: {}", id);
        return repository.findById(id).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * Exclui um usuário por ID.
     */
    public boolean excluirUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuário não encontrado para exclusão: " + id);
        }

        repository.deleteById(id);
        log.info("🗑️ Usuário excluído com sucesso: ID {}", id);
        return true;
    }

    // ============================================
    // 🔄 Conversão
    // ============================================

    /**
     * Converte a entidade User para o DTO de resposta.
     */
    private UserResponseDTO toDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }
}

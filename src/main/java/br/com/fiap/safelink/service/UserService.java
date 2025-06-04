package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.UserRequestDTO;
import br.com.fiap.safelink.dto.response.UserResponseDTO;
import br.com.fiap.safelink.exception.UsuarioNotFoundException;
import br.com.fiap.safelink.model.User;
import br.com.fiap.safelink.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * # 👤 Service: UserService
 *
 * Camada de regras de negócio para a entidade `User`.
 * Fornece operações de CRUD, filtros paginados, conversão DTO/entidade e criptografia de senha.
 *
 * ---
 * 🔐 Garante unicidade de e-mail e validações de negócio
 * 🔄 Utiliza ModelMapper para conversão automática
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // ============================================
    // ➕ Criação
    // ============================================

    /**
     * Registra um novo usuário no sistema.
     *
     * - Verifica unicidade de e-mail
     * - Criptografa a senha
     *
     * @param dto dados do usuário
     * @return DTO do usuário criado
     */
    @Transactional
    public UserResponseDTO gravar(UserRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado.");
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
     * Atualiza os dados de um usuário.
     *
     * - Valida duplicidade de e-mail
     * - Recriptografa a senha
     *
     * @param id  identificador do usuário
     * @param dto novos dados
     * @return DTO atualizado
     */
    @Transactional
    public UserResponseDTO atualizar(Long id, UserRequestDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!user.getEmail().equals(dto.getEmail()) && repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já está em uso por outro usuário.");
        }

        modelMapper.map(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = repository.save(user);

        log.info("✏️ Usuário atualizado com sucesso: ID {}", user.getId());
        return toDTO(user);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * Lista todos os usuários sem paginação.
     */
    public List<UserResponseDTO> consultarTodos() {
        log.info("📋 Listando todos os usuários");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * Lista usuários com paginação.
     */
    public Page<UserResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📄 Listando usuários paginados");
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Consulta usuário por ID.
     */
    public UserResponseDTO consultarPorId(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        log.info("🔍 Usuário encontrado: ID {}", id);
        return toDTO(user);
    }

    /**
     * Retorna a entidade `User` pura (uso interno).
     */
    public User buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * Exclui um usuário do sistema.
     *
     * @param id identificador do usuário
     */
    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuário não encontrado para exclusão: " + id);
        }
        repository.deleteById(id);
        log.info("🗑️ Usuário excluído com sucesso: ID {}", id);
    }

    // ============================================
    // 🔄 Conversão
    // ============================================

    /**
     * Converte a entidade `User` em `UserResponseDTO`.
     */
    private UserResponseDTO toDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }
}

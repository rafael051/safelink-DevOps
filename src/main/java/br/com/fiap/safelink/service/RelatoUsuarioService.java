package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.RelatoUsuarioRequestDTO;
import br.com.fiap.safelink.dto.response.RelatoUsuarioResponseDTO;
import br.com.fiap.safelink.exception.RelatoUsuarioNotFoundException;
import br.com.fiap.safelink.exception.RegiaoNotFoundException;
import br.com.fiap.safelink.filter.RelatoUsuarioFilter;
import br.com.fiap.safelink.model.RelatoUsuario;
import br.com.fiap.safelink.model.Regiao;
import br.com.fiap.safelink.model.User;
import br.com.fiap.safelink.repository.RelatoUsuarioRepository;
import br.com.fiap.safelink.repository.RegiaoRepository;
import br.com.fiap.safelink.specification.RelatoUsuarioSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * # 🗣️ Service: RelatoUsuarioService
 *
 * Camada de regras de negócio responsável por manipular a entidade `RelatoUsuario`.
 * Realiza persistência, mapeamentos DTO↔Entidade, preenchimento de relacionamentos e
 * consultas com ou sem filtro dinâmico.
 *
 * ---
 * 🔐 Todos os relatos são vinculados ao usuário autenticado.
 * 🌎 Cada relato pertence a uma região geográfica.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RelatoUsuarioService {

    private final RelatoUsuarioRepository repository;
    private final RegiaoRepository regiaoRepository;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * Grava um novo relato vinculado ao usuário autenticado e à região informada.
     */
    @Transactional
    public RelatoUsuarioResponseDTO gravar(RelatoUsuarioRequestDTO dto) {
        log.info("📥 Criando novo relato de usuário para região ID {}", dto.getRegiaoId());

        RelatoUsuario relato = modelMapper.map(dto, RelatoUsuario.class);
        preencherRelacionamentos(relato, dto);

        relato = repository.save(relato);
        log.info("✅ Relato criado com sucesso: ID {}", relato.getId());

        return toDTO(relato);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * Atualiza um relato de usuário já existente.
     */
    @Transactional
    public RelatoUsuarioResponseDTO atualizar(Long id, RelatoUsuarioRequestDTO dto) {
        log.info("✏️ Atualizando relato ID {}", id);

        RelatoUsuario relato = repository.findById(id)
                .orElseThrow(() -> new RelatoUsuarioNotFoundException(id));

        modelMapper.map(dto, relato);
        preencherRelacionamentos(relato, dto);

        relato = repository.save(relato);
        log.info("✅ Relato atualizado com sucesso: ID {}", relato.getId());

        return toDTO(relato);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    public Page<RelatoUsuarioResponseDTO> consultarComFiltro(RelatoUsuarioFilter filtro, Pageable pageable) {
        log.info("🔍 Consultando relatos com filtro: {}", filtro);

        Specification<RelatoUsuario> spec = RelatoUsuarioSpecification.withFilters(filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    public RelatoUsuarioResponseDTO consultarPorId(Long id) {
        log.info("🔎 Consultando relato por ID: {}", id);

        RelatoUsuario relato = repository.findById(id)
                .orElseThrow(() -> new RelatoUsuarioNotFoundException(id));

        return toDTO(relato);
    }

    public List<RelatoUsuarioResponseDTO> consultarTodos() {
        log.info("📋 Listando todos os relatos de usuários cadastrados");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Page<RelatoUsuarioResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📄 Consulta paginada de relatos de usuários");
        return repository.findAll(pageable).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    @Transactional
    public void excluir(Long id) {
        log.info("❌ Excluindo relato ID: {}", id);

        if (!repository.existsById(id)) {
            throw new RelatoUsuarioNotFoundException("Relato não encontrado para exclusão: " + id);
        }

        repository.deleteById(id);
        log.info("🗑️ Relato excluído com sucesso: ID {}", id);
    }

    // ============================================
    // 🧩 Relacionamentos
    // ============================================

    /**
     * Preenche a região e o usuário autenticado no relato.
     */
    private void preencherRelacionamentos(RelatoUsuario relato, RelatoUsuarioRequestDTO dto) {
        Regiao regiao = regiaoRepository.findById(dto.getRegiaoId())
                .orElseThrow(() -> new RegiaoNotFoundException("Região não encontrada: ID " + dto.getRegiaoId()));

        User usuario = getUsuarioAutenticado();

        relato.setRegiao(regiao);
        relato.setUsuario(usuario);
    }

    /**
     * Obtém o usuário autenticado do contexto de segurança.
     */
    private User getUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        throw new IllegalStateException("Usuário autenticado inválido.");
    }

    // ============================================
    // 🔄 Conversão
    // ============================================

    /**
     * Converte a entidade `RelatoUsuario` para o DTO de resposta.
     */
    private RelatoUsuarioResponseDTO toDTO(RelatoUsuario relato) {
        return modelMapper.map(relato, RelatoUsuarioResponseDTO.class);
    }
}

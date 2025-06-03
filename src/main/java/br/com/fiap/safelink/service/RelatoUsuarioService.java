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

import java.util.List;

/**
 * # 🗣️ Service: RelatoUsuarioService
 *
 * Camada de regras de negócio responsável por manipular a entidade `RelatoUsuario`.
 * Executa validações, mapeamentos DTO↔Entidade, persistência, filtros dinâmicos e preenchimento de relacionamentos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RelatoUsuarioService {

    // ============================================
    // 🔗 Injeção de dependências
    // ============================================

    private final RelatoUsuarioRepository repository;
    private final RegiaoRepository regiaoRepository;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * ### 📌 Gravar novo relato de usuário
     *
     * - Converte DTO em entidade
     * - Preenche relacionamentos (região + usuário autenticado)
     * - Persiste e retorna DTO
     */
    public RelatoUsuarioResponseDTO gravar(RelatoUsuarioRequestDTO dto) {
        RelatoUsuario relato = modelMapper.map(dto, RelatoUsuario.class);
        preencherRelacionamentos(relato, dto);
        relato = repository.save(relato);
        log.info("✅ Relato de usuário criado com sucesso: ID {}", relato.getId());
        return toDTO(relato);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * ### ✏️ Atualizar relato existente
     */
    public RelatoUsuarioResponseDTO atualizar(Long id, RelatoUsuarioRequestDTO dto) {
        RelatoUsuario relato = repository.findById(id)
                .orElseThrow(() -> new RelatoUsuarioNotFoundException(id));
        modelMapper.map(dto, relato);
        preencherRelacionamentos(relato, dto);
        relato = repository.save(relato);
        log.info("✏️ Relato de usuário atualizado: ID {}", relato.getId());
        return toDTO(relato);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    public Page<RelatoUsuarioResponseDTO> consultarComFiltro(RelatoUsuarioFilter filtro, Pageable pageable) {
        Specification<RelatoUsuario> spec = RelatoUsuarioSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtros: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    public RelatoUsuarioResponseDTO consultarPorId(Long id) {
        RelatoUsuario relato = repository.findById(id)
                .orElseThrow(() -> new RelatoUsuarioNotFoundException(id));
        log.info("🔎 Relato de usuário localizado: ID {}", id);
        return toDTO(relato);
    }

    public List<RelatoUsuarioResponseDTO> consultarTodos() {
        log.info("📋 Listando todos os relatos de usuários cadastrados");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Page<RelatoUsuarioResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📋 Listando relatos de usuários (paginado simples)");
        return repository.findAll(pageable).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RelatoUsuarioNotFoundException("Relato não encontrado para exclusão: " + id);
        }
        repository.deleteById(id);
        log.info("🗑️ Relato de usuário excluído: ID {}", id);
    }

    // ============================================
    // 🧩 Relacionamentos
    // ============================================

    /**
     * ### 🧩 Preencher relacionamentos (região + usuário autenticado)
     */
    private void preencherRelacionamentos(RelatoUsuario relato, RelatoUsuarioRequestDTO dto) {
        Regiao regiao = regiaoRepository.findById(dto.getRegiaoId())
                .orElseThrow(() -> new RegiaoNotFoundException("Região não encontrada: ID " + dto.getRegiaoId()));

        User usuario = getUsuarioAutenticado();

        relato.setRegiao(regiao);
        relato.setUsuario(usuario);
    }

    /**
     * ### 🔐 Obter usuário autenticado do contexto de segurança
     */
    private User getUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        throw new IllegalStateException("Usuário autenticado inválido.");
    }

    // ============================================
    // 🔄 Conversão auxiliar
    // ============================================

    private RelatoUsuarioResponseDTO toDTO(RelatoUsuario relato) {
        return modelMapper.map(relato, RelatoUsuarioResponseDTO.class);
    }
}

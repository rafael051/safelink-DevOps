package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.PrevisaoRiscoRequestDTO;
import br.com.fiap.safelink.dto.response.PrevisaoRiscoResponseDTO;
import br.com.fiap.safelink.exception.PrevisaoRiscoNotFoundException;
import br.com.fiap.safelink.filter.PrevisaoRiscoFilter;
import br.com.fiap.safelink.model.PrevisaoRisco;
import br.com.fiap.safelink.repository.PrevisaoRiscoRepository;
import br.com.fiap.safelink.specification.PrevisaoRiscoSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * # ☁️ Service: PrevisaoRiscoService
 *
 * Camada de regras de negócio responsável por manipular a entidade `PrevisaoRisco`.
 * Realiza validações, conversões DTO↔Entidade, filtros e persistência no banco.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrevisaoRiscoService {

    // ============================================
    // 🔗 Injeção de dependências
    // ============================================

    private final PrevisaoRiscoRepository repository;
    private final RegiaoService regiaoService;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * ### 📌 Gravar nova previsão de risco
     *
     * - Converte o DTO para entidade
     * - Preenche os relacionamentos (Região)
     * - Persiste no banco
     * - Retorna DTO de resposta
     */
    public PrevisaoRiscoResponseDTO gravar(PrevisaoRiscoRequestDTO dto) {
        PrevisaoRisco previsao = modelMapper.map(dto, PrevisaoRisco.class);
        preencherRelacionamentos(previsao, dto);
        previsao = repository.save(previsao);
        log.info("✅ Previsão de risco registrada: ID {}", previsao.getId());
        return toDTO(previsao);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * ### ✏️ Atualizar previsão de risco existente
     *
     * - Verifica existência
     * - Aplica alterações do DTO
     * - Persiste novamente no banco
     */
    public PrevisaoRiscoResponseDTO atualizar(Long id, PrevisaoRiscoRequestDTO dto) {
        PrevisaoRisco previsao = repository.findById(id)
                .orElseThrow(() -> new PrevisaoRiscoNotFoundException(id));
        modelMapper.map(dto, previsao);
        preencherRelacionamentos(previsao, dto);
        previsao = repository.save(previsao);
        log.info("✏️ Previsão de risco atualizada: ID {}", previsao.getId());
        return toDTO(previsao);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * ### 🔍 Consultar com filtros dinâmicos
     */
    public Page<PrevisaoRiscoResponseDTO> consultarComFiltro(PrevisaoRiscoFilter filtro, Pageable pageable) {
        Specification<PrevisaoRisco> spec = PrevisaoRiscoSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtros: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * ### 🔎 Consultar por ID
     */
    public PrevisaoRiscoResponseDTO consultarPorId(Long id) {
        PrevisaoRisco previsao = repository.findById(id)
                .orElseThrow(() -> new PrevisaoRiscoNotFoundException(id));
        log.info("🔎 Previsão de risco encontrada: ID {}", id);
        return toDTO(previsao);
    }

    /**
     * ### 📋 Listar todas as previsões (sem filtro)
     */
    public List<PrevisaoRiscoResponseDTO> consultarTodas() {
        log.info("📋 Listando todas as previsões de risco");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * ### 📋 Listar previsões com paginação simples
     */
    public Page<PrevisaoRiscoResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📋 Listando previsões de risco (paginado simples)");
        return repository.findAll(pageable).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * ### 🗑️ Excluir previsão de risco
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new PrevisaoRiscoNotFoundException("Previsão de risco não encontrada para exclusão: " + id);
        }
        repository.deleteById(id);
        log.info("🗑️ Previsão de risco excluída com sucesso: ID {}", id);
    }

    // ============================================
    // 🧩 Relacionamentos
    // ============================================

    /**
     * ### 🧩 Preencher relacionamentos (Região)
     */
    private void preencherRelacionamentos(PrevisaoRisco previsao, PrevisaoRiscoRequestDTO dto) {
        if (dto.getRegiaoId() != null) {
            previsao.setRegiao(regiaoService.buscarEntidadePorId(dto.getRegiaoId()));
        }
    }

    // ============================================
    // 🔄 Conversão auxiliar
    // ============================================

    /**
     * ### 🔄 Conversão Entidade → DTO
     */
    private PrevisaoRiscoResponseDTO toDTO(PrevisaoRisco previsao) {
        return modelMapper.map(previsao, PrevisaoRiscoResponseDTO.class);
    }
}

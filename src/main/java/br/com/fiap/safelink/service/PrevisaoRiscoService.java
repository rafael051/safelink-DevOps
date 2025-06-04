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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * # ☁️ Service: PrevisaoRiscoService
 *
 * Responsável por aplicar as regras de negócio sobre a entidade `PrevisaoRisco`.
 * Realiza persistência, preenchimento de relacionamentos com `Regiao`, e consultas com filtros dinâmicos.
 *
 * ---
 * 🔁 Conversão automática com ModelMapper
 * 📦 Integração com `RegiaoService` para validação de relacionamentos
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrevisaoRiscoService {

    private final PrevisaoRiscoRepository repository;
    private final RegiaoService regiaoService;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * Cria uma nova previsão de risco.
     *
     * @param dto dados da previsão
     * @return previsão salva convertida em DTO
     */
    @Transactional
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
     * Atualiza uma previsão de risco existente.
     *
     * @param id  ID da previsão
     * @param dto novos dados
     * @return DTO da previsão atualizada
     */
    @Transactional
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
     * Consulta previsões com filtros dinâmicos.
     */
    public Page<PrevisaoRiscoResponseDTO> consultarComFiltro(PrevisaoRiscoFilter filtro, Pageable pageable) {
        Specification<PrevisaoRisco> spec = PrevisaoRiscoSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtros: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * Consulta previsão de risco por ID.
     */
    public PrevisaoRiscoResponseDTO consultarPorId(Long id) {
        PrevisaoRisco previsao = repository.findById(id)
                .orElseThrow(() -> new PrevisaoRiscoNotFoundException(id));
        log.info("🔎 Previsão de risco encontrada: ID {}", id);
        return toDTO(previsao);
    }

    /**
     * Lista todas as previsões sem filtro.
     */
    public List<PrevisaoRiscoResponseDTO> consultarTodas() {
        log.info("📋 Listando todas as previsões de risco");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * Lista previsões com paginação simples.
     */
    public Page<PrevisaoRiscoResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📄 Listando previsões de risco paginadas");
        return repository.findAll(pageable).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * Exclui uma previsão de risco.
     */
    @Transactional
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
     * Preenche os relacionamentos da entidade (Região).
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
     * Converte a entidade `PrevisaoRisco` para DTO de resposta.
     */
    private PrevisaoRiscoResponseDTO toDTO(PrevisaoRisco previsao) {
        return modelMapper.map(previsao, PrevisaoRiscoResponseDTO.class);
    }
}

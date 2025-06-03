package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.AlertaRequestDTO;
import br.com.fiap.safelink.dto.response.AlertaResponseDTO;
import br.com.fiap.safelink.exception.AlertaNotFoundException;
import br.com.fiap.safelink.filter.AlertaFilter;
import br.com.fiap.safelink.model.Alerta;
import br.com.fiap.safelink.repository.AlertaRepository;
import br.com.fiap.safelink.specification.AlertaSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * # 🧠 Service: AlertaService
 *
 * Camada de regras de negócio da entidade `Alerta`.
 * Responsável por validação, mapeamento DTO ↔ Entidade, persistência e relacionamentos.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaService {

    // 🔗 Injeção de dependências
    private final AlertaRepository repository;
    private final ModelMapper modelMapper;
    private final RegiaoService regiaoService;


    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * Grava um novo alerta no sistema.
     */
    public AlertaResponseDTO gravarAlerta(AlertaRequestDTO dto) {
        Alerta alerta = modelMapper.map(dto, Alerta.class);
        preencherRelacionamentos(alerta, dto);
        alerta = repository.save(alerta);
        log.info("✅ Alerta criado com sucesso: ID {}", alerta.getId());
        return toDTO(alerta);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * Atualiza um alerta existente.
     */
    public AlertaResponseDTO atualizarAlerta(Long id, AlertaRequestDTO dto) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new AlertaNotFoundException(id));
        modelMapper.map(dto, alerta);
        preencherRelacionamentos(alerta, dto);
        alerta = repository.save(alerta);
        log.info("✏️ Alerta atualizado com sucesso: ID {}", alerta.getId());
        return toDTO(alerta);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * Consulta paginada de alertas (sem filtros).
     */
    @Cacheable(value = "alertasTodos", key = "'pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()")
    public Page<AlertaResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📄 Consulta paginada (sem filtros): página={}, tamanho={}, ordenação={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Consulta paginada com filtros dinâmicos.
     */
    public Page<AlertaResponseDTO> consultarComFiltro(AlertaFilter filtro, Pageable pageable) {
        Specification<Alerta> spec = AlertaSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtros: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * Consulta de um alerta por ID.
     */
    public AlertaResponseDTO consultarPorId(Long id) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new AlertaNotFoundException(id));
        log.info("🔎 Alerta encontrado: ID {}", id);
        return toDTO(alerta);
    }

    /**
     * Consulta completa (sem filtros, sem paginação).
     */
    public List<AlertaResponseDTO> consultarTodos() {
        log.info("📃 Consulta total de alertas (sem paginação)");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * Exclui um alerta por ID.
     */
    public void excluirAlerta(Long id) {
        if (!repository.existsById(id)) {
            throw new AlertaNotFoundException("Alerta não encontrado para exclusão: " + id);
        }
        repository.deleteById(id);
        log.info("🗑️ Alerta excluído com sucesso: ID {}", id);
    }

    // ============================================
    // 🧩 Relacionamentos
    // ============================================

    /**
     * Preenche os relacionamentos de região e tipo de evento,
     * validando a existência dos IDs recebidos no DTO.
     */
    private void preencherRelacionamentos(Alerta alerta, AlertaRequestDTO dto) {
        // 🔗 Associação com Região (obrigatória)
        if (dto.getIdRegiao() != null) {
            alerta.setRegiao(regiaoService.buscarEntidadePorId(dto.getIdRegiao()));

        }
    }


    // ============================================
    // 🔄 Conversão
    // ============================================

    /**
     * Converte a entidade Alerta para o DTO de resposta.
     */
    private AlertaResponseDTO toDTO(Alerta alerta) {
        return modelMapper.map(alerta, AlertaResponseDTO.class);
    }
}

package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.RegiaoRequestDTO;
import br.com.fiap.safelink.dto.response.RegiaoResponseDTO;
import br.com.fiap.safelink.exception.RegiaoNotFoundException;
import br.com.fiap.safelink.filter.RegiaoFilter;
import br.com.fiap.safelink.model.Regiao;
import br.com.fiap.safelink.repository.RegiaoRepository;
import br.com.fiap.safelink.specification.RegiaoSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * # 🗺️ Service: RegiaoService
 *
 * Camada de regras de negócio para a entidade `Regiao`.
 * Realiza mapeamentos, persistência e filtros dinâmicos com Specification.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegiaoService {

    // ============================================
    // 🔗 Dependências
    // ============================================

    private final RegiaoRepository repository;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * ### 📌 Gravar nova região
     *
     * Persiste uma nova região no banco e retorna DTO de resposta.
     */
    public RegiaoResponseDTO gravar(RegiaoRequestDTO dto) {
        Regiao regiao = modelMapper.map(dto, Regiao.class);
        regiao = repository.save(regiao);
        log.info("✅ Região gravada com sucesso: ID {}", regiao.getId());
        return toDTO(regiao);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * ### ✏️ Atualizar região existente
     *
     * Localiza e atualiza os dados da região informada.
     */
    public RegiaoResponseDTO atualizar(Long id, RegiaoRequestDTO dto) {
        Regiao regiao = repository.findById(id)
                .orElseThrow(() -> new RegiaoNotFoundException(id));
        modelMapper.map(dto, regiao);
        regiao = repository.save(regiao);
        log.info("✏️ Região atualizada: ID {}", regiao.getId());
        return toDTO(regiao);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * ### 🔍 Consulta com filtros dinâmicos
     */
    public Page<RegiaoResponseDTO> consultarComFiltro(RegiaoFilter filtro, Pageable pageable) {
        Specification<Regiao> spec = RegiaoSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtros: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * ### 🔎 Consultar por ID
     */
    public RegiaoResponseDTO consultarPorId(Long id) {
        Regiao regiao = repository.findById(id)
                .orElseThrow(() -> new RegiaoNotFoundException(id));
        log.info("🔎 Região encontrada: ID {}", id);
        return toDTO(regiao);
    }

    /**
     * ### 📋 Listar todas as regiões
     */
    public List<RegiaoResponseDTO> consultarTodas() {
        log.info("📋 Listando todas as regiões cadastradas");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * ### 📋 Listar regiões com paginação simples
     */
    public Page<RegiaoResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📋 Listando regiões (paginado simples)");
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * ### 📦 Buscar entidade completa (uso interno)
     *
     * Utilizado em relacionamentos como EventoNatural ou PrevisaoRisco.
     */
    public Regiao buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegiaoNotFoundException(id));
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * ### 🗑️ Excluir região
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RegiaoNotFoundException("Região não encontrada para exclusão: " + id);
        }
        repository.deleteById(id);
        log.info("🗑️ Região excluída com sucesso: ID {}", id);
    }

    // ============================================
    // 🔄 Conversão auxiliar
    // ============================================

    /**
     * ### 🔄 Conversão entidade → DTO
     */
    private RegiaoResponseDTO toDTO(Regiao regiao) {
        return modelMapper.map(regiao, RegiaoResponseDTO.class);
    }
}

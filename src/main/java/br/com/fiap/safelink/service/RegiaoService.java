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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * # 🗺️ Service: RegiaoService
 *
 * Camada responsável por aplicar as regras de negócio da entidade `Regiao`.
 * Oferece operações de CRUD e suporte a filtros dinâmicos com Specification.
 *
 * ---
 * 🔁 Converte automaticamente entre entidade e DTO.
 * 🧩 Utilizada por serviços que dependem de localização geográfica.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegiaoService {

    private final RegiaoRepository repository;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * Grava uma nova região no sistema.
     *
     * @param dto dados da nova região
     * @return região salva convertida em DTO
     */
    @Transactional
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
     * Atualiza uma região existente no banco.
     *
     * @param id  identificador da região
     * @param dto novos dados
     * @return DTO da região atualizada
     */
    @Transactional
    public RegiaoResponseDTO atualizar(Long id, RegiaoRequestDTO dto) {
        Regiao regiao = repository.findById(id)
                .orElseThrow(() -> new RegiaoNotFoundException(id));

        modelMapper.map(dto, regiao);
        regiao = repository.save(regiao);

        log.info("✏️ Região atualizada com sucesso: ID {}", regiao.getId());
        return toDTO(regiao);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * Consulta regiões com filtros dinâmicos (Specification).
     */
    public Page<RegiaoResponseDTO> consultarComFiltro(RegiaoFilter filtro, Pageable pageable) {
        Specification<Regiao> spec = RegiaoSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtro: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * Consulta região por ID.
     */
    public RegiaoResponseDTO consultarPorId(Long id) {
        Regiao regiao = repository.findById(id)
                .orElseThrow(() -> new RegiaoNotFoundException(id));
        log.info("🔎 Região encontrada: ID {}", id);
        return toDTO(regiao);
    }

    /**
     * Lista todas as regiões (sem paginação).
     */
    public List<RegiaoResponseDTO> consultarTodas() {
        log.info("📋 Listando todas as regiões cadastradas");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * Lista regiões com paginação simples.
     */
    public Page<RegiaoResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📄 Listando regiões paginadas");
        return repository.findAll(pageable).map(this::toDTO);
    }

    /**
     * Retorna a entidade `Regiao` pura (uso interno).
     * Ex: utilizada em relacionamentos com `EventoNatural`.
     */
    public Regiao buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegiaoNotFoundException(id));
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * Exclui uma região do sistema.
     */
    @Transactional
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
     * Converte a entidade `Regiao` para DTO de resposta.
     */
    private RegiaoResponseDTO toDTO(Regiao regiao) {
        return modelMapper.map(regiao, RegiaoResponseDTO.class);
    }
}

package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.EventoNaturalRequestDTO;
import br.com.fiap.safelink.dto.response.EventoNaturalResponseDTO;
import br.com.fiap.safelink.exception.EventoNaturalNotFoundException;
import br.com.fiap.safelink.filter.EventoNaturalFilter;
import br.com.fiap.safelink.model.EventoNatural;
import br.com.fiap.safelink.repository.EventoNaturalRepository;
import br.com.fiap.safelink.specification.EventoNaturalSpecification;
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
 * # 🌪️ Service: EventoNaturalService
 *
 * Camada de regras de negócio responsável por manipular a entidade `EventoNatural`.
 * Gerencia persistência, atualização, consultas com filtros e relacionamentos com `Regiao`.
 *
 * ---
 * ✅ Usa ModelMapper para conversões
 * 📦 Integra com RegiaoService para consistência relacional
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventoNaturalService {

    private final EventoNaturalRepository repository;
    private final RegiaoService regiaoService;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * Registra um novo evento natural no sistema.
     *
     * @param dto dados recebidos via API
     * @return DTO da entidade salva
     */
    @Transactional
    public EventoNaturalResponseDTO gravar(EventoNaturalRequestDTO dto) {
        EventoNatural evento = modelMapper.map(dto, EventoNatural.class);
        preencherRelacionamentos(evento, dto);
        evento = repository.save(evento);
        log.info("✅ Evento natural registrado: ID {}", evento.getId());
        return toDTO(evento);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * Atualiza os dados de um evento natural existente.
     *
     * @param id  ID do evento
     * @param dto dados atualizados
     * @return DTO com os dados persistidos
     */
    @Transactional
    public EventoNaturalResponseDTO atualizar(Long id, EventoNaturalRequestDTO dto) {
        EventoNatural evento = repository.findById(id)
                .orElseThrow(() -> new EventoNaturalNotFoundException(id));

        modelMapper.map(dto, evento);
        preencherRelacionamentos(evento, dto);
        evento = repository.save(evento);

        log.info("✏️ Evento natural atualizado: ID {}", evento.getId());
        return toDTO(evento);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * Consulta eventos naturais com filtros dinâmicos (Specification).
     */
    public Page<EventoNaturalResponseDTO> consultarComFiltro(EventoNaturalFilter filtro, Pageable pageable) {
        Specification<EventoNatural> spec = EventoNaturalSpecification.withFilters(filtro);
        log.info("🔍 Consulta de eventos com filtro: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * Consulta evento por ID.
     */
    public EventoNaturalResponseDTO consultarPorId(Long id) {
        EventoNatural evento = repository.findById(id)
                .orElseThrow(() -> new EventoNaturalNotFoundException(id));
        log.info("🔎 Evento natural encontrado: ID {}", id);
        return toDTO(evento);
    }

    /**
     * Lista todos os eventos naturais (sem filtro).
     */
    public List<EventoNaturalResponseDTO> consultarTodos() {
        log.info("📋 Listando todos os eventos naturais");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * Lista eventos com paginação simples (sem filtros).
     */
    public Page<EventoNaturalResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📄 Listando eventos naturais paginados");
        return repository.findAll(pageable).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * Remove um evento natural do sistema.
     *
     * @param id identificador do evento
     */
    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new EventoNaturalNotFoundException("Evento natural não encontrado para exclusão: " + id);
        }

        repository.deleteById(id);
        log.info("🗑️ Evento natural excluído: ID {}", id);
    }

    // ============================================
    // 🧩 Relacionamentos
    // ============================================

    /**
     * Preenche a região vinculada ao evento natural.
     *
     * @param evento entidade sendo manipulada
     * @param dto    dados recebidos da requisição
     */
    private void preencherRelacionamentos(EventoNatural evento, EventoNaturalRequestDTO dto) {
        if (dto.getRegiaoId() != null) {
            evento.setRegiao(regiaoService.buscarEntidadePorId(dto.getRegiaoId()));
        }
    }

    // ============================================
    // 🔄 Conversão
    // ============================================

    /**
     * Converte a entidade EventoNatural para o DTO de resposta.
     */
    private EventoNaturalResponseDTO toDTO(EventoNatural evento) {
        return modelMapper.map(evento, EventoNaturalResponseDTO.class);
    }
}

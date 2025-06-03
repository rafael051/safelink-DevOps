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

import java.util.List;

/**
 * # 🌪️ Service: EventoNaturalService
 *
 * Camada de regras de negócio responsável por manipular a entidade `EventoNatural`.
 * Realiza validações, preenchimento de FKs, mapeamentos DTO↔Entidade e delega persistência ao repositório.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventoNaturalService {

    // ============================================
    // 🔗 Injeção de dependências
    // ============================================

    private final EventoNaturalRepository repository;
    private final RegiaoService regiaoService;
    private final ModelMapper modelMapper;

    // ============================================
    // 📌 Criação
    // ============================================

    /**
     * ### 📌 Gravar novo evento natural
     *
     * - Converte o DTO para entidade
     * - Preenche relacionamentos
     * - Persiste no banco
     * - Retorna DTO de resposta
     */
    public EventoNaturalResponseDTO gravar(EventoNaturalRequestDTO dto) {
        EventoNatural evento = modelMapper.map(dto, EventoNatural.class);
        preencherRelacionamentos(evento, dto);
        evento = repository.save(evento);
        log.info("✅ Evento natural registrado com sucesso: ID {}", evento.getId());
        return toDTO(evento);
    }

    // ============================================
    // ✏️ Atualização
    // ============================================

    /**
     * ### ✏️ Atualizar evento natural
     *
     * - Verifica existência
     * - Aplica alterações do DTO
     * - Salva novamente e retorna DTO atualizado
     */
    public EventoNaturalResponseDTO atualizar(Long id, EventoNaturalRequestDTO dto) {
        EventoNatural evento = repository.findById(id)
                .orElseThrow(() -> new EventoNaturalNotFoundException(id));
        modelMapper.map(dto, evento);
        preencherRelacionamentos(evento, dto);
        evento = repository.save(evento);
        log.info("✏️ Evento natural atualizado com sucesso: ID {}", evento.getId());
        return toDTO(evento);
    }

    // ============================================
    // 🔍 Consultas
    // ============================================

    /**
     * ### 🔍 Consulta com filtros dinâmicos
     *
     * Permite buscar eventos com base nos critérios do filtro.
     */
    public Page<EventoNaturalResponseDTO> consultarComFiltro(EventoNaturalFilter filtro, Pageable pageable) {
        Specification<EventoNatural> spec = EventoNaturalSpecification.withFilters(filtro);
        log.info("🔍 Consulta com filtros dinâmicos: {}", filtro);
        return repository.findAll(spec, pageable).map(this::toDTO);
    }

    /**
     * ### 🔍 Consultar por ID
     */
    public EventoNaturalResponseDTO consultarPorId(Long id) {
        EventoNatural evento = repository.findById(id)
                .orElseThrow(() -> new EventoNaturalNotFoundException(id));
        log.info("🔎 Evento natural encontrado: ID {}", id);
        return toDTO(evento);
    }

    /**
     * ### 📋 Consulta completa (sem filtros, sem paginação)
     */
    public List<EventoNaturalResponseDTO> consultarTodos() {
        log.info("📋 Listando todos os eventos naturais cadastrados");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    /**
     * ### 📋 Consulta paginada simples (sem filtro)
     */
    public Page<EventoNaturalResponseDTO> consultarPaginado(Pageable pageable) {
        log.info("📋 Listando eventos naturais (paginado simples)");
        return repository.findAll(pageable).map(this::toDTO);
    }

    // ============================================
    // 🗑️ Exclusão
    // ============================================

    /**
     * ### 🗑️ Excluir evento natural
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new EventoNaturalNotFoundException("Evento natural não encontrado para exclusão: " + id);
        }
        repository.deleteById(id);
        log.info("🗑️ Evento natural excluído com sucesso: ID {}", id);
    }

    // ============================================
    // 🧩 Relacionamentos externos
    // ============================================

    /**
     * ### 🧩 Preencher relacionamentos
     */
    private void preencherRelacionamentos(EventoNatural evento, EventoNaturalRequestDTO dto) {
        if (dto.getRegiaoId() != null) {
            evento.setRegiao(regiaoService.buscarEntidadePorId(dto.getRegiaoId()));
        }
    }

    // ============================================
    // 🔄 Conversão auxiliar
    // ============================================

    /**
     * ### 🔄 Conversão Entidade → DTO
     */
    private EventoNaturalResponseDTO toDTO(EventoNatural evento) {
        return modelMapper.map(evento, EventoNaturalResponseDTO.class);
    }
}

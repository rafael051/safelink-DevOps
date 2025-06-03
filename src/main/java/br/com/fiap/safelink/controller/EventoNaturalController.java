package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.EventoNaturalRequestDTO;
import br.com.fiap.safelink.dto.response.EventoNaturalResponseDTO;
import br.com.fiap.safelink.filter.EventoNaturalFilter;
import br.com.fiap.safelink.service.EventoNaturalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * # 🌪️ Controller: EventoNaturalController
 *
 * Responsável por expor os endpoints REST para gerenciamento de `EventoNatural`.
 * Inclui operações CRUD, busca com filtros e cache.
 *
 * ---
 * 🔐 Todos os endpoints exigem autenticação via JWT
 * 🌐 CORS liberado para http://localhost:3000
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "6 - Eventos Naturais", description = "Endpoints relacionados ao registro de eventos naturais extremos")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/eventos-naturais")
@RequiredArgsConstructor
public class EventoNaturalController {

    private final EventoNaturalService service;

    // ============================================
    // 📌 POST /eventos-naturais
    // ============================================

    /**
     * ### 📌 Registrar novo evento natural
     */
    @PostMapping
    @CacheEvict(value = "eventosNaturais", allEntries = true)
    @Operation(
            summary = "Registrar evento natural",
            description = "Cria um novo evento natural no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Evento natural registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<EventoNaturalResponseDTO> gravar(@RequestBody @Valid EventoNaturalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravar(dto));
    }

    // ============================================
    // 📋 GET /eventos-naturais (sem filtro, paginado)
    // ============================================

    /**
     * ### 📋 Listar todos os eventos naturais (paginado)
     */
    @GetMapping
    @Operation(
            summary = "Listar eventos naturais (paginado)",
            description = "Retorna todos os eventos naturais, com suporte à paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de eventos retornada com sucesso")
            }
    )
    public ResponseEntity<Page<EventoNaturalResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 📄 GET /eventos-naturais/filtro
    // ============================================

    /**
     * ### 📄 Consultar eventos com filtros e paginação
     */
    @GetMapping("/filtro")
    @Cacheable(
            value = "eventosNaturais",
            key = "'spec_'+#filter.toString()+'_pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()"
    )
    @Operation(
            summary = "Listar eventos com filtros",
            description = "Consulta eventos naturais por múltiplos critérios, com paginação e ordenação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista filtrada de eventos retornada com sucesso")
            }
    )
    public ResponseEntity<Page<EventoNaturalResponseDTO>> listarComFiltro(
            @ParameterObject EventoNaturalFilter filter,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(service.consultarComFiltro(filter, pageable));
    }

    // ============================================
    // 🔍 GET /eventos-naturais/{id}
    // ============================================

    /**
     * ### 🔍 Buscar evento por ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar evento por ID",
            description = "Retorna os dados do evento natural correspondente ao ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento natural encontrado"),
                    @ApiResponse(responseCode = "404", description = "Evento natural não encontrado")
            }
    )
    public ResponseEntity<EventoNaturalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    // ============================================
    // ✏️ PUT /eventos-naturais/{id}
    // ============================================

    /**
     * ### ✏️ Atualizar evento existente
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "eventosNaturais", allEntries = true)
    @Operation(
            summary = "Atualizar evento natural",
            description = "Atualiza os dados de um evento natural existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evento natural atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
                    @ApiResponse(responseCode = "404", description = "Evento natural não encontrado")
            }
    )
    public ResponseEntity<EventoNaturalResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EventoNaturalRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ============================================
    // 🗑️ DELETE /eventos-naturais/{id}
    // ============================================

    /**
     * ### 🗑️ Excluir evento natural
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "eventosNaturais", allEntries = true)
    @Operation(
            summary = "Excluir evento natural",
            description = "Remove um evento natural do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Evento natural removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Evento natural não encontrado")
            }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

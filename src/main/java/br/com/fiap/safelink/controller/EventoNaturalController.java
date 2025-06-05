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
 * Controlador REST para a entidade `EventoNatural`, que representa ocorrências reais de eventos extremos.
 *
 * ---
 * ## 🔐 Segurança
 * Todos os endpoints requerem autenticação via JWT.
 *
 * ---
 * ## 🌐 Acesso externo
 * CORS liberado para `http://localhost:3000`.
 *
 * ---
 * ## Funcionalidades
 * - Criar, atualizar e excluir eventos naturais
 * - Consultar com e sem filtros (paginado)
 * - Utiliza cache para melhorar desempenho em consultas
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "6 - Eventos Naturais", description = "Endpoints relacionados ao registro de eventos naturais extremos")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://safelink-production.up.railway.app"
})

@RestController
@RequestMapping("/eventos-naturais")
@RequiredArgsConstructor
public class EventoNaturalController {

    private final EventoNaturalService service;

    // ============================================
    // 📌 POST /eventos-naturais
    // ============================================

    /**
     * ## 📌 Criar novo evento natural
     *
     * Registra um novo evento natural no sistema.
     *
     * - Requisição: JSON com dados válidos
     * - Resposta: objeto DTO com dados persistidos
     * - HTTP: `201 Created` em caso de sucesso
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
    // 📋 GET /eventos-naturais
    // ============================================

    /**
     * ## 📋 Listar eventos naturais (paginado)
     *
     * Retorna todos os eventos cadastrados, com suporte a paginação.
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
    // 🔍 GET /eventos-naturais/filtro
    // ============================================

    /**
     * ## 🔍 Consultar eventos com filtros dinâmicos
     *
     * Permite aplicar múltiplos critérios de busca com paginação.
     *
     * Exemplos de filtros:
     * - Tipo de evento
     * - Intervalo de datas
     * - Região
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
    // 🔎 GET /eventos-naturais/{id}
    // ============================================

    /**
     * ## 🔎 Buscar evento por ID
     *
     * Recupera um evento natural específico pelo identificador único.
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
     * ## ✏️ Atualizar evento natural
     *
     * Atualiza os dados de um evento natural já existente.
     *
     * - Requisição: JSON com dados atualizados
     * - Retorna os dados atualizados com `200 OK`
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
     * ## 🗑️ Excluir evento natural
     *
     * Remove um evento natural com base no seu ID.
     * - Retorna `204 No Content` em caso de sucesso.
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "eventosNaturais", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir evento natural",
            description = "Remove um evento natural do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Evento natural removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Evento natural não encontrado")
            }
    )
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.AlertaRequestDTO;
import br.com.fiap.safelink.dto.response.AlertaResponseDTO;
import br.com.fiap.safelink.filter.AlertaFilter;
import br.com.fiap.safelink.service.AlertaService;
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
 * # 📢 Controller: AlertaController
 *
 * Responsável por expor os endpoints REST para gerenciamento da entidade `Alerta`.
 * Suporta operações de criação, leitura com e sem filtros, atualização e exclusão.
 *
 * ---
 * 🔐 Todos os endpoints exigem autenticação via JWT
 * 🌐 CORS liberado para http://localhost:3000
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "4 - Alertas", description = "Endpoints relacionados ao gerenciamento de alertas de risco")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService service;

    // ============================================
    // 📌 POST /alertas
    // ============================================

    /**
     * ### 📌 Cadastrar novo alerta
     */
    @PostMapping
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(
            summary = "Cadastrar alerta",
            description = "Registra um novo alerta de risco no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<AlertaResponseDTO> gravar(@RequestBody @Valid AlertaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravarAlerta(dto));
    }

    // ============================================
    // 📋 GET /alertas (sem filtro, paginado)
    // ============================================

    /**
     * ### 📋 Listar todos os alertas (paginado)
     */
    @GetMapping
    @Operation(
            summary = "Listar alertas (paginado)",
            description = "Retorna todos os alertas cadastrados, com suporte à paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de alertas retornada com sucesso")
            }
    )
    public ResponseEntity<Page<AlertaResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 📄 GET /alertas/filtro
    // ============================================

    /**
     * ### 📄 Consultar alertas com filtros e paginação
     */
    @GetMapping("/filtro")
    @Cacheable(
            value = "alertas",
            key = "'spec_'+#filter.toString()+'_pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()"
    )
    @Operation(
            summary = "Listar alertas com filtros",
            description = "Consulta alertas por múltiplos critérios, com paginação e ordenação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista filtrada de alertas retornada com sucesso")
            }
    )
    public ResponseEntity<Page<AlertaResponseDTO>> listarComFiltro(
            @ParameterObject AlertaFilter filter,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(service.consultarComFiltro(filter, pageable));
    }

    // ============================================
    // 🔍 GET /alertas/{id}
    // ============================================

    /**
     * ### 🔍 Buscar alerta por ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar alerta por ID",
            description = "Retorna os dados do alerta correspondente ao ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Alerta encontrado"),
                    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
            }
    )
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    // ============================================
    // ✏️ PUT /alertas/{id}
    // ============================================

    /**
     * ### ✏️ Atualizar alerta existente
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(
            summary = "Atualizar alerta",
            description = "Atualiza os dados de um alerta existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
                    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
            }
    )
    public ResponseEntity<AlertaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AlertaRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarAlerta(id, dto));
    }

    // ============================================
    // 🗑️ DELETE /alertas/{id}
    // ============================================

    /**
     * ### 🗑️ Excluir alerta
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(
            summary = "Excluir alerta",
            description = "Remove um alerta de risco do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Alerta removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
            }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirAlerta(id);
        return ResponseEntity.noContent().build();
    }
}

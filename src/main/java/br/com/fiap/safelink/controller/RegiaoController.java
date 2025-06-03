package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.RegiaoRequestDTO;
import br.com.fiap.safelink.dto.response.RegiaoResponseDTO;
import br.com.fiap.safelink.filter.RegiaoFilter;
import br.com.fiap.safelink.service.RegiaoService;
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
 * # 🗺️ Controller: RegiaoController
 *
 * Responsável por expor os endpoints REST para gerenciamento da entidade `Regiao`.
 * Fornece operações de criação, listagem com e sem filtros, consulta por ID, atualização e exclusão.
 *
 * ---
 * 🔐 Todos os endpoints exigem autenticação via JWT
 * 🌐 CORS liberado para http://localhost:3000
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "3 - Regiões", description = "Endpoints relacionados ao cadastro e gerenciamento de regiões geográficas")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/regioes")
@RequiredArgsConstructor
public class RegiaoController {

    private final RegiaoService service;

    // ============================================
    // 📌 POST /regioes
    // ============================================

    /**
     * ### 📌 Cadastrar nova região
     */
    @PostMapping
    @CacheEvict(value = "regioes", allEntries = true)
    @Operation(
            summary = "Cadastrar região",
            description = "Registra uma nova região no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Região criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<RegiaoResponseDTO> gravar(@RequestBody @Valid RegiaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravar(dto));
    }

    // ============================================
    // 📋 GET /regioes (sem filtro, paginado)
    // ============================================

    /**
     * ### 📋 Listar todas as regiões (paginado)
     */
    @GetMapping
    @Operation(
            summary = "Listar regiões (paginado)",
            description = "Retorna todas as regiões cadastradas, com suporte à paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de regiões retornada com sucesso")
            }
    )
    public ResponseEntity<Page<RegiaoResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 📄 GET /regioes/filtro
    // ============================================

    /**
     * ### 📄 Consultar regiões com filtros dinâmicos
     */
    @GetMapping("/filtro")
    @Cacheable(
            value = "regioes",
            key = "'spec_'+#filter.toString()+'_pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()"
    )
    @Operation(
            summary = "Listar regiões com filtros dinâmicos",
            description = "Permite consultar regiões por múltiplos critérios, com suporte à paginação e ordenação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista filtrada de regiões retornada com sucesso")
            }
    )
    public ResponseEntity<Page<RegiaoResponseDTO>> listarComFiltro(
            @ParameterObject RegiaoFilter filter,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(service.consultarComFiltro(filter, pageable));
    }

    // ============================================
    // 🔍 GET /regioes/{id}
    // ============================================

    /**
     * ### 🔍 Buscar região por ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar região por ID",
            description = "Retorna os dados da região correspondente ao ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Região encontrada"),
                    @ApiResponse(responseCode = "404", description = "Região não encontrada")
            }
    )
    public ResponseEntity<RegiaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    // ============================================
    // ✏️ PUT /regioes/{id}
    // ============================================

    /**
     * ### ✏️ Atualizar região existente
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "regioes", allEntries = true)
    @Operation(
            summary = "Atualizar região",
            description = "Atualiza os dados de uma região existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Região atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
                    @ApiResponse(responseCode = "404", description = "Região não encontrada")
            }
    )
    public ResponseEntity<RegiaoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RegiaoRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ============================================
    // 🗑️ DELETE /regioes/{id}
    // ============================================

    /**
     * ### 🗑️ Excluir região
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "regioes", allEntries = true)
    @Operation(
            summary = "Excluir região",
            description = "Remove uma região do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Região removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Região não encontrada")
            }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

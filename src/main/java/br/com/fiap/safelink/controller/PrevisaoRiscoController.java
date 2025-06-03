package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.PrevisaoRiscoRequestDTO;
import br.com.fiap.safelink.dto.response.PrevisaoRiscoResponseDTO;
import br.com.fiap.safelink.filter.PrevisaoRiscoFilter;
import br.com.fiap.safelink.service.PrevisaoRiscoService;
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
 * # 🔮 Controller: PrevisaoRiscoController
 *
 * Responsável por expor os endpoints REST para gerenciamento da entidade `PrevisaoRisco`.
 * Oferece operações de criação, consulta (simples ou com filtros), atualização e exclusão.
 *
 * ---
 * 🔐 Todos os endpoints exigem autenticação via JWT (Bearer Token)
 * 🌐 CORS liberado para http://localhost:3000
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "7 - Previsões de Risco", description = "Endpoints relacionados à geração e gerenciamento de previsões de risco")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/previsoes-risco")
@RequiredArgsConstructor
public class PrevisaoRiscoController {

    private final PrevisaoRiscoService service;

    // ============================================
    // 📌 POST /previsoes-risco
    // ============================================

    /**
     * ### 📌 Cadastrar nova previsão de risco
     */
    @PostMapping
    @CacheEvict(value = "previsoesRisco", allEntries = true)
    @Operation(
            summary = "Cadastrar previsão de risco",
            description = "Registra uma nova previsão de risco no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Previsão criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<PrevisaoRiscoResponseDTO> gravar(@RequestBody @Valid PrevisaoRiscoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravar(dto));
    }

    // ============================================
    // 📋 GET /previsoes-risco (sem filtro, paginado)
    // ============================================

    /**
     * ### 📋 Listar todas as previsões de risco (paginado)
     */
    @GetMapping
    @Operation(
            summary = "Listar previsões de risco (paginado)",
            description = "Retorna todas as previsões de risco do sistema, com suporte à paginação e ordenação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de previsões retornada com sucesso")
            }
    )
    public ResponseEntity<Page<PrevisaoRiscoResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 📄 GET /previsoes-risco/filtro
    // ============================================

    /**
     * ### 📄 Consultar previsões com filtros dinâmicos
     */
    @GetMapping("/filtro")
    @Cacheable(
            value = "previsoesRisco",
            key = "'spec_'+#filter.toString()+'_pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()"
    )
    @Operation(
            summary = "Listar previsões com filtros dinâmicos",
            description = "Permite consultar previsões de risco por múltiplos critérios, com paginação e ordenação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista filtrada de previsões retornada com sucesso")
            }
    )
    public ResponseEntity<Page<PrevisaoRiscoResponseDTO>> listarComFiltro(
            @ParameterObject PrevisaoRiscoFilter filter,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(service.consultarComFiltro(filter, pageable));
    }

    // ============================================
    // 🔍 GET /previsoes-risco/{id}
    // ============================================

    /**
     * ### 🔍 Buscar previsão por ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar previsão por ID",
            description = "Retorna os dados da previsão de risco correspondente ao ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Previsão encontrada"),
                    @ApiResponse(responseCode = "404", description = "Previsão não encontrada")
            }
    )
    public ResponseEntity<PrevisaoRiscoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    // ============================================
    // ✏️ PUT /previsoes-risco/{id}
    // ============================================

    /**
     * ### ✏️ Atualizar previsão de risco existente
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "previsoesRisco", allEntries = true)
    @Operation(
            summary = "Atualizar previsão de risco",
            description = "Atualiza os dados de uma previsão de risco existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Previsão atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
                    @ApiResponse(responseCode = "404", description = "Previsão não encontrada")
            }
    )
    public ResponseEntity<PrevisaoRiscoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PrevisaoRiscoRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ============================================
    // 🗑️ DELETE /previsoes-risco/{id}
    // ============================================

    /**
     * ### 🗑️ Excluir previsão de risco
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "previsoesRisco", allEntries = true)
    @Operation(
            summary = "Excluir previsão de risco",
            description = "Remove uma previsão de risco do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Previsão removida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Previsão não encontrada")
            }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

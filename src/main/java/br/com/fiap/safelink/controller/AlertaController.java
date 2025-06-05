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
 * Camada responsável por expor os endpoints REST relacionados à entidade `Alerta`.
 *
 * ---
 * ## 🔐 Segurança
 * Todos os endpoints exigem autenticação via JWT.
 *
 * ---
 * ## 🌐 Acesso
 * CORS liberado para `http://localhost:3000`.
 *
 * ---
 * ## 🔄 Funcionalidades expostas:
 * - Criar novo alerta
 * - Consultar alertas (todos, por ID, com filtros e paginação)
 * - Atualizar alerta existente
 * - Excluir alerta
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "4 - Alertas", description = "Endpoints para gerenciamento de alertas de risco emitidos")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://safelink-production.up.railway.app"
})

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService service;

    // ============================================
    // 📌 POST /alertas
    // ============================================

    /**
     * ## 📌 Criar novo alerta
     *
     * Cadastra um novo alerta de risco no sistema.
     *
     * - Requisição: JSON com dados válidos do alerta
     * - Resposta: DTO do alerta criado
     * - HTTP: `201 Created` em caso de sucesso
     */
    @PostMapping
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(
            summary = "Cadastrar alerta",
            description = "Cria e registra um novo alerta de risco.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<AlertaResponseDTO> gravar(@RequestBody @Valid AlertaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravarAlerta(dto));
    }

    // ============================================
    // 📋 GET /alertas
    // ============================================

    /**
     * ## 📋 Listar todos os alertas (paginado)
     *
     * Retorna todos os alertas do sistema de forma paginada, sem filtros.
     *
     * - Suporte a paginação, ordenação e tamanho de página.
     * - Recomendado para uso geral e painéis administrativos.
     */
    @GetMapping
    @Operation(
            summary = "Listar alertas (paginado)",
            description = "Retorna todos os alertas cadastrados com suporte à paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de alertas retornada com sucesso")
            }
    )
    public ResponseEntity<Page<AlertaResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 🔍 GET /alertas/filtro
    // ============================================

    /**
     * ## 🔍 Consultar alertas com filtros dinâmicos
     *
     * Permite buscar alertas com base em múltiplos critérios, como:
     * - Região
     * - Nível de risco
     * - Intervalo de datas
     *
     * Suporta paginação e ordenação.
     */
    @GetMapping("/filtro")
    @Cacheable(
            value = "alertas",
            key = "'spec_'+#filter.toString()+'_pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()"
    )
    @Operation(
            summary = "Listar alertas com filtros",
            description = "Consulta alertas com múltiplos critérios, ordenação e paginação.",
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
    // 🔎 GET /alertas/{id}
    // ============================================

    /**
     * ## 🔎 Buscar alerta por ID
     *
     * Consulta os dados de um alerta específico, informando o `ID` na URL.
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
     * ## ✏️ Atualizar alerta
     *
     * Atualiza um alerta existente no sistema.
     *
     * - Necessário fornecer o ID via path.
     * - Requisição deve conter os novos dados válidos.
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
     * ## 🗑️ Excluir alerta
     *
     * Remove um alerta de risco com base no seu identificador.
     *
     * - Após a exclusão, a resposta HTTP será `204 No Content`.
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "alertas", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir alerta",
            description = "Remove um alerta do sistema com base no ID informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Alerta removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
            }
    )
    public void excluir(@PathVariable Long id) {
        service.excluirAlerta(id);
    }
}

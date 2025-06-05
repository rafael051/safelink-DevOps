package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.RelatoUsuarioRequestDTO;
import br.com.fiap.safelink.dto.response.RelatoUsuarioResponseDTO;
import br.com.fiap.safelink.filter.RelatoUsuarioFilter;
import br.com.fiap.safelink.service.RelatoUsuarioService;
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
 * # 🗣️ Controller: RelatoUsuarioController
 *
 * Controlador REST responsável pelo gerenciamento da entidade `RelatoUsuario`.
 *
 * ---
 * ## 🔐 Segurança
 * - Todos os endpoints requerem autenticação via JWT
 *
 * ## 🌐 CORS
 * - Libera acesso do frontend local: http://localhost:3000
 *
 * ## 📚 Funcionalidades
 * - Criar, consultar, listar (com e sem filtro), atualizar e excluir relatos enviados por usuários
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "5 - Relatos de Usuário", description = "Endpoints relacionados aos relatos enviados por cidadãos e agentes de campo")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://<seu-projeto>.up.railway.app"
})

@RestController
@RequestMapping("/relatos-usuario")
@RequiredArgsConstructor
public class RelatoUsuarioController {

    private final RelatoUsuarioService service;

    // ============================================
    // 📌 POST /relatos-usuario
    // ============================================

    /**
     * ## 📌 Cadastrar novo relato de usuário
     *
     * Registra um novo relato de condição de risco relatado por um cidadão ou agente.
     *
     * - Requisição: JSON contendo descrição, localização e tipo de risco
     * - Resposta: DTO com ID e dados do relato criado
     * - HTTP: 201 Created
     */
    @PostMapping
    @CacheEvict(value = "relatosUsuario", allEntries = true)
    @Operation(
            summary = "Cadastrar relato de usuário",
            description = "Registra um novo relato de condição de risco enviado por um usuário.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Relato criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<RelatoUsuarioResponseDTO> gravar(@RequestBody @Valid RelatoUsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravar(dto));
    }

    // ============================================
    // 📋 GET /relatos-usuario
    // ============================================

    /**
     * ## 📋 Listar todos os relatos (sem filtro)
     *
     * Retorna todos os relatos cadastrados, com suporte a paginação e ordenação.
     */
    @GetMapping
    @Operation(
            summary = "Listar relatos (paginado)",
            description = "Retorna todos os relatos cadastrados, com suporte à paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de relatos retornada com sucesso")
            }
    )
    public ResponseEntity<Page<RelatoUsuarioResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 📄 GET /relatos-usuario/filtro
    // ============================================

    /**
     * ## 📄 Consultar relatos com filtros dinâmicos
     *
     * Permite filtrar relatos por atributos como data, tipo, descrição ou localização.
     *
     * - Utiliza Specification + Pageable
     * - Cache ativado para performance
     */
    @GetMapping("/filtro")
    @Cacheable(
            value = "relatosUsuario",
            key = "'spec_'+#filter.toString()+'_pagina_'+#pageable.pageNumber+'_tamanho_'+#pageable.pageSize+'_ordenacao_'+#pageable.sort.toString()"
    )
    @Operation(
            summary = "Listar relatos com filtros dinâmicos",
            description = "Permite consultar relatos por múltiplos critérios, com suporte à paginação e ordenação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista filtrada de relatos retornada com sucesso")
            }
    )
    public ResponseEntity<Page<RelatoUsuarioResponseDTO>> listarComFiltro(
            @ParameterObject RelatoUsuarioFilter filter,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(service.consultarComFiltro(filter, pageable));
    }

    // ============================================
    // 🔍 GET /relatos-usuario/{id}
    // ============================================

    /**
     * ## 🔍 Buscar relato por ID
     *
     * Retorna os dados detalhados de um relato a partir do ID informado.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar relato por ID",
            description = "Retorna os dados do relato correspondente ao ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Relato encontrado"),
                    @ApiResponse(responseCode = "404", description = "Relato não encontrado")
            }
    )
    public ResponseEntity<RelatoUsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    // ============================================
    // ✏️ PUT /relatos-usuario/{id}
    // ============================================

    /**
     * ## ✏️ Atualizar relato existente
     *
     * Atualiza o conteúdo de um relato previamente cadastrado.
     *
     * - Requisição: JSON com novos dados
     * - Resposta: DTO atualizado
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "relatosUsuario", allEntries = true)
    @Operation(
            summary = "Atualizar relato",
            description = "Atualiza os dados de um relato de usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Relato atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
                    @ApiResponse(responseCode = "404", description = "Relato não encontrado")
            }
    )
    public ResponseEntity<RelatoUsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RelatoUsuarioRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ============================================
    // 🗑️ DELETE /relatos-usuario/{id}
    // ============================================

    /**
     * ## 🗑️ Excluir relato
     *
     * Remove um relato do sistema com base no ID.
     *
     * - HTTP: 204 No Content
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "relatosUsuario", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir relato",
            description = "Remove um relato do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Relato removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Relato não encontrado")
            }
    )
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

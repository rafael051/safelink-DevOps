package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.UserRequestDTO;
import br.com.fiap.safelink.dto.response.UserResponseDTO;
import br.com.fiap.safelink.service.UserService;
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
 * # 👤 Controller: UserController
 *
 * Camada REST responsável pelo gerenciamento da entidade `User`.
 *
 * ---
 * ## 🔐 Segurança
 * - Todos os endpoints exigem autenticação JWT
 *
 * ## 🌐 CORS
 * - Permite acesso de frontend local em `http://localhost:3000`
 *
 * ## 📚 Funcionalidades
 * - Criar, consultar, listar (com ou sem filtro), atualizar e excluir usuários autenticáveis
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "2 - Usuários", description = "Endpoints para gerenciamento de usuários autenticáveis")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    // ============================================
    // 📌 POST /users
    // ============================================

    /**
     * ## 📌 Criar novo usuário
     *
     * Registra um novo usuário no sistema com dados de e-mail, senha e role.
     *
     * - HTTP: 201 Created
     */
    @PostMapping
    @CacheEvict(value = "users", allEntries = true)
    @Operation(
            summary = "Criar novo usuário",
            description = "Registra um novo usuário com e-mail, senha e papel.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<UserResponseDTO> gravar(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gravar(dto));
    }

    // ============================================
    // 📋 GET /users
    // ============================================

    /**
     * ## 📋 Listar todos os usuários (paginado)
     */
    @GetMapping
    @Operation(
            summary = "Listar usuários (paginado)",
            description = "Retorna todos os usuários cadastrados com suporte à paginação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
            }
    )
    public ResponseEntity<Page<UserResponseDTO>> listarTodosPaginado(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.consultarPaginado(pageable));
    }

    // ============================================
    // 🔎 GET /users/{id}
    // ============================================

    /**
     * ## 🔎 Buscar usuário por ID
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados do usuário correspondente ao ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    // ============================================
    // ✏️ PUT /users/{id}
    // ============================================

    /**
     * ## ✏️ Atualizar usuário
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "users", allEntries = true)
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    public ResponseEntity<UserResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // ============================================
    // 🗑️ DELETE /users/{id}
    // ============================================

    /**
     * ## 🗑️ Excluir usuário
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "users", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * # 👤 Controller: UserController
 *
 * Responsável por expor os endpoints REST para gerenciamento da entidade `User`.
 * Suporta operações de criação, leitura, atualização e exclusão de usuários autenticáveis.
 *
 * ---
 * 🔐 Todos os endpoints exigem autenticação via JWT
 * 🌐 CORS liberado para http://localhost:3000
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "2 - Usuários", description = "Endpoints para gerenciamento de usuários autenticáveis")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    // ============================================
    // 📌 POST /users
    // ============================================

    /**
     * ### 📌 Criar novo usuário
     */
    @PostMapping
    @Operation(
            summary = "Criar novo usuário",
            description = "Registra um novo usuário com e-mail, senha e papel.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    public ResponseEntity<UserResponseDTO> criar(@RequestBody @Valid UserRequestDTO dto) {
        log.info("👤 Criando novo usuário: {}", dto.getEmail());
        return ResponseEntity.ok(service.criarUsuario(dto));
    }

    // ============================================
    // 📋 GET /users
    // ============================================

    /**
     * ### 📋 Listar todos os usuários
     */
    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
            }
    )
    public ResponseEntity<List<UserResponseDTO>> listarTodos() {
        log.info("📋 Listando todos os usuários.");
        return ResponseEntity.ok(service.listarTodos());
    }

    // ============================================
    // 🔍 GET /users/{id}
    // ============================================

    /**
     * ### 🔍 Buscar usuário por ID
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
        log.info("🔍 Buscando usuário com ID: {}", id);
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("⚠️ Usuário não encontrado para ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // ============================================
    // ✏️ PUT /users/{id}
    // ============================================

    /**
     * ### ✏️ Atualizar usuário existente
     */
    @PutMapping("/{id}")
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
        log.info("🔄 Atualizando usuário ID: {}", id);
        return service.atualizarUsuario(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("⚠️ Usuário não encontrado para atualização: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // ============================================
    // 🗑️ DELETE /users/{id}
    // ============================================

    /**
     * ### 🗑️ Excluir usuário
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário do sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("❌ Excluindo usuário ID: {}", id);
        boolean excluido = service.excluirUsuario(id);
        return excluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

package br.com.fiap.safelink.controller;

import br.com.fiap.safelink.dto.request.AlertaRequestDTO;
import br.com.fiap.safelink.dto.response.AlertaResponseDTO;
import br.com.fiap.safelink.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ## 📢 Controller: AlertaController
 *
 * Controlador responsável pelos endpoints REST da entidade Alerta.
 * Permite cadastrar, listar, buscar, atualizar e remover alertas de risco.
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Alertas", description = "Endpoints relacionados ao gerenciamento de alertas de risco")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private static final Logger log = LoggerFactory.getLogger(AlertaController.class);
    private final AlertaService service;

    /**
     * ### 📅 POST /alertas
     * Cadastra um novo alerta.
     */
    @PostMapping
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(summary = "Cadastrar alerta", description = "Registra um novo alerta de risco no sistema.")
    public ResponseEntity<AlertaResponseDTO> gravar(@RequestBody @Valid AlertaRequestDTO dto) {
        log.info("📅 Cadastrando alerta: {}", dto);
        return ResponseEntity.ok(service.gravarAlerta(dto));
    }

    /**
     * ### 📄 GET /alertas
     * Lista todos os alertas cadastrados.
     */
    @GetMapping
    @Cacheable("alertas")
    @Operation(summary = "Listar todos os alertas", description = "Retorna todos os alertas cadastrados.")
    public ResponseEntity<List<AlertaResponseDTO>> listarTodos() {
        log.info("📄 Listando todos os alertas.");
        return ResponseEntity.ok(service.consultarTodos());
    }

    /**
     * ### 🔍 GET /alertas/{id}
     * Retorna um alerta específico por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID", description = "Retorna os dados do alerta correspondente ao ID informado.")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("🔍 Buscando alerta ID: {}", id);
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    /**
     * ### ✏️ PUT /alertas/{id}
     * Atualiza um alerta existente pelo ID.
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(summary = "Atualizar alerta", description = "Atualiza os dados de um alerta existente.")
    public ResponseEntity<AlertaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AlertaRequestDTO dto) {
        log.info("✏️ Atualizando alerta ID: {}", id);
        return ResponseEntity.ok(service.atualizarAlerta(id, dto));
    }

    /**
     * ### 🗑️ DELETE /alertas/{id}
     * Remove um alerta do sistema.
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "alertas", allEntries = true)
    @Operation(summary = "Excluir alerta", description = "Remove um alerta de risco do sistema.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("🗑️ Excluindo alerta ID: {}", id);
        service.excluirAlerta(id); // lança exceção se não existir
        return ResponseEntity.noContent().build();
    }
}

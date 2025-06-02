package br.com.fiap.safelink.service;

import br.com.fiap.safelink.dto.request.AlertaRequestDTO;
import br.com.fiap.safelink.dto.response.AlertaResponseDTO;
import br.com.fiap.safelink.exception.AlertaNotFoundException;
import br.com.fiap.safelink.model.Alerta;
import br.com.fiap.safelink.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * # 📢 Serviço: AlertaService
 *
 * Camada de regra de negócio para a entidade Alerta.
 * Responsável por:
 * - Converter DTO <-> Entidade
 * - Validar campos e integridade
 * - Tratar exceções de forma clara
 * - Delegar persistência ao repositório
 *
 * ---
 * @author Rafael
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlertaService {

    // ============================
    // 🔗 Injeção de Dependências
    // ============================

    private final AlertaRepository repository;
    private final ModelMapper modelMapper;

    // ============================
    // 📌 Gravar novo alerta
    // ============================

    public AlertaResponseDTO gravarAlerta(AlertaRequestDTO dto) {
        log.info("📌 Gravando novo alerta: {}", dto);

        Alerta alerta = modelMapper.map(dto, Alerta.class);
        preencherRelacionamentos(alerta, dto);
        alerta = repository.save(alerta);
        return toDTO(alerta);
    }

    // ============================
    // 🔁 Atualizar alerta existente
    // ============================

    public AlertaResponseDTO atualizarAlerta(Long id, AlertaRequestDTO dto) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new AlertaNotFoundException(id));

        modelMapper.map(dto, alerta);
        preencherRelacionamentos(alerta, dto);
        alerta = repository.save(alerta);
        return toDTO(alerta);
    }

    // ============================
    // 🔍 Consultas
    // ============================

    public List<AlertaResponseDTO> consultarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public AlertaResponseDTO consultarPorId(Long id) {
        Alerta alerta = repository.findById(id)
                .orElseThrow(() -> new AlertaNotFoundException(id));
        return toDTO(alerta);
    }

    // ============================
    // ❌ Excluir alerta
    // ============================

    public void excluirAlerta(Long id) {
        if (!repository.existsById(id)) {
            throw new AlertaNotFoundException("Alerta não encontrado para exclusão: " + id);
        }
        repository.deleteById(id);
    }

    // ============================
    // 🧩 Preencher relações externas (FKs)
    // ============================

    private void preencherRelacionamentos(Alerta alerta, AlertaRequestDTO dto) {
        // Exemplo: relacionamento com TipoAlerta ou Regiao
        // alerta.setTipo(tipoService.buscarPorId(dto.getIdTipo()));
    }

    // ============================
    // 🔄 Conversão para DTO
    // ============================

    private AlertaResponseDTO toDTO(Alerta alerta) {
        return modelMapper.map(alerta, AlertaResponseDTO.class);
    }
}

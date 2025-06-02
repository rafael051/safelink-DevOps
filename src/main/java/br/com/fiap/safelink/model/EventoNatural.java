package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 🌪️ Entidade: EventoNatural
 *
 * Representa uma ocorrência registrada de um fenômeno natural extremo,
 * como enchente, deslizamento, vendaval ou tempestade severa.
 *
 * ---
 * ## 📌 Utilização
 * - Usada para registrar eventos climáticos que já aconteceram.
 * - Base para geração de relatórios e visualização histórica.
 * - Serve como insumo para modelos preditivos e emissão de alertas.
 *
 * ---
 * ## 🔗 Relacionamentos
 * - N:1 com `Regiao`
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_evento_natural")
public class EventoNatural {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único do evento natural (chave primária). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===========================
    // 🌩️ Dados do Evento
    // ===========================

    /** Tipo do evento ocorrido (ex: Enchente, Deslizamento, Vendaval). */
    @NotBlank(message = "O tipo do evento é obrigatório.")
    private String tipo;

    /** Descrição detalhada do ocorrido (opcional). */
    private String descricao;

    /** Data e hora em que o evento aconteceu. */
    @NotNull(message = "A data de ocorrência é obrigatória.")
    private LocalDateTime dataOcorrencia;

    // ===========================
    // 🔗 Relacionamentos
    // ===========================

    /** Região onde o evento foi registrado. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "regiao_id", nullable = false)
    private Regiao regiao;
}

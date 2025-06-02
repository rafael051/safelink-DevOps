package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 🔮 Entidade: PrevisaoRisco
 *
 * Representa uma previsão de risco futuro associada a uma determinada região,
 * podendo ser gerada por algoritmos, fontes meteorológicas, ou inteligência artificial.
 *
 * ---
 * ## 📌 Utilização
 * - Usada para antecipar situações de risco (ex: risco ALTO de enchente nas próximas 24h).
 * - Permite disparar alertas com antecedência.
 * - Serve como base para planejamento da Defesa Civil ou orientações à população.
 *
 * ---
 * ## 🔗 Relacionamentos
 * - N:1 com `Regiao`
 *
 * ---
 * @version 1.0
 * @author Rafael
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_previsao_risco")
public class PrevisaoRisco {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único da previsão de risco. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===========================
    // 📊 Detalhes da Previsão
    // ===========================

    /**
     * Nível de risco previsto.
     * Ex: ALTO, MÉDIO, BAIXO, CRÍTICO.
     */
    @NotBlank(message = "O nível previsto de risco é obrigatório.")
    private String nivelPrevisto;

    /**
     * Fonte da previsão (opcional).
     * Pode indicar o modelo, serviço meteorológico ou técnica usada.
     * Ex: "IA - Modelo SafeLink V2", "INMET", "ClimaTempo"
     */
    private String fonte;

    /**
     * Data e hora em que a previsão foi gerada.
     * Usado para controlar atualizações ou expiração de previsões antigas.
     */
    @NotNull(message = "A data de geração da previsão é obrigatória.")
    private LocalDateTime geradoEm;

    // ===========================
    // 🔗 Relacionamento
    // ===========================

    /**
     * Região associada à previsão de risco.
     * Cada previsão está vinculada a uma e somente uma região.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "regiao_id", nullable = false)
    private Regiao regiao;
}

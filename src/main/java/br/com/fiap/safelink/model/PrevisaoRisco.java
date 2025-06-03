package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(name = "id_previsao_risco")
    private Long id;

    // ===========================
    // 📊 Detalhes da Previsão
    // ===========================

    /** Nível de risco previsto. Ex: ALTO, MÉDIO, BAIXO, CRÍTICO. */
    @NotBlank(message = "O nível previsto de risco é obrigatório.")
    @Column(name = "ds_nivel_previsto", nullable = false)
    private String nivelPrevisto;

    /** Fonte da previsão (modelo, serviço meteorológico ou técnica usada). */
    @Column(name = "ds_fonte")
    private String fonte;

    /** Data e hora em que a previsão foi gerada. */
    @NotNull(message = "A data de geração da previsão é obrigatória.")
    @Column(name = "dt_gerado_em", nullable = false)
    private LocalDateTime geradoEm;

    // ===========================
    // 🌍 Relacionamento com Região
    // ===========================

    /** Região associada à previsão de risco. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    // ===========================
    // 🕒 Controle de criação (opcional)
    // ===========================

    /** Timestamp automático de criação da previsão (auditável). */
    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false)
    private LocalDateTime dataCriacao;

}

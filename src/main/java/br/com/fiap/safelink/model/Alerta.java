package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * # 🚨 Entidade: Alerta
 *
 * Representa um alerta preventivo ou emergencial emitido pelo sistema para uma determinada região.
 * Contém informações essenciais como tipo do evento, nível de risco, mensagem personalizada e horário de emissão.
 *
 * ---
 * ## 🔗 Relacionamentos
 * - N:1 com `Regiao`
 *
 * ---
 * ## 🎯 Finalidade
 * Informar autoridades, moradores ou agentes públicos sobre situações de risco,
 * possibilitando ações rápidas de evacuação, preparação ou resposta.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_alerta")
public class Alerta {

    // ===========================
    // 🔑 Identificação do Alerta
    // ===========================

    /** Identificador único do alerta (chave primária). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===========================
    // 📝 Informações do Alerta
    // ===========================

    /** Tipo de alerta (ex: Enchente, Deslizamento, Tempestade). */
    @NotBlank(message = "O tipo do alerta é obrigatório.")
    private String tipo;

    /** Nível de risco associado ao alerta (ex: ALTO, MÉDIO, BAIXO). */
    @NotBlank(message = "O nível de risco é obrigatório.")
    private String nivelRisco;

    /** Mensagem personalizada exibida para os usuários da região. */
    @NotBlank(message = "A mensagem do alerta é obrigatória.")
    private String mensagem;

    /** Data e hora em que o alerta foi emitido pelo sistema. */
    @NotNull(message = "A data de emissão do alerta é obrigatória.")
    private LocalDateTime emitidoEm;

    // ===========================
    // 🔗 Relacionamentos
    // ===========================

    /** Região à qual o alerta se refere. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "regiao_id", nullable = false)
    private Regiao regiao;
}

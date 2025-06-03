package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * # 🚨 Entidade: Alerta
 *
 * Representa um alerta preventivo ou emergencial emitido pelo sistema para uma determinada região.
 * Inclui dados de risco, mensagem e referências à região.
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
@Table(name = "tb_alerta")
public class Alerta {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** ID único do alerta */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    // ===========================
    // 📝 Detalhes do Alerta
    // ===========================

    /** Nível de risco do alerta (ex: ALTO, MODERADO, BAIXO) */
    @NotBlank(message = "O nível de risco é obrigatório.")
    @Column(name = "ds_nivel_risco", nullable = false)
    private String nivelRisco;

    /** Mensagem explicativa sobre o alerta emitido */
    @NotBlank(message = "A mensagem do alerta é obrigatória.")
    @Column(name = "ds_mensagem", nullable = false)
    private String mensagem;

    /** Data e hora da emissão do alerta */
    @NotNull(message = "A data de emissão do alerta é obrigatória.")
    @Column(name = "dt_emitido_em", nullable = false)
    private LocalDateTime emitidoEm;

    // ===========================
    // 🌍 Relacionamento com Região
    // ===========================

    /** Região afetada pelo alerta */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    // ===========================
    // 🕒 Controle de criação (opcional)
    // ===========================

    /** Timestamp de criação do alerta */
    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false)
    private LocalDateTime dataCriacao;

}

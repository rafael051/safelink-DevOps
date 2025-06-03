package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * # 📢 Entidade: RelatoUsuario
 *
 * Representa um relato enviado por um cidadão ou agente de campo através do sistema.
 * Esse relato pode conter informações úteis como observações sobre inundações, rachaduras,
 * deslizamentos ou qualquer outra condição de risco visualizada.
 *
 * ---
 * ## 📌 Utilização
 * - Coleta de dados comunitários (crowdsourcing).
 * - Complementa os dados técnicos com informações humanas e locais.
 * - Pode alimentar a geração de alertas e planos de resposta.
 *
 * ---
 * ## 🔗 Relacionamentos
 * - N:1 com `User`
 * - N:1 com `Regiao`
 *
 * ---
 * @version 1.0
 * @autor Rafael
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_relato_usuario")
public class RelatoUsuario {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único do relato enviado (chave primária). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relato_usuario")
    private Long id;

    // ===========================
    // 💬 Conteúdo do Relato
    // ===========================

    /** Texto do relato descritivo. */
    @NotBlank(message = "A mensagem do relato é obrigatória.")
    @Column(name = "ds_mensagem", nullable = false)
    private String mensagem;

    /** Data e hora em que o relato foi feito. */
    @NotNull(message = "A data do relato é obrigatória.")
    @Column(name = "dt_relato", nullable = false)
    private LocalDateTime dataRelato;

    // ===========================
    // 🔗 Relacionamentos
    // ===========================

    /** Usuário que realizou o relato. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    /** Região onde o relato foi feito ou observado. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_regiao", nullable = false)
    private Regiao regiao;

    // ===========================
    // 🕒 Controle de criação (opcional)
    // ===========================

    /** Timestamp de criação do registro do relato. */
    @CreationTimestamp
    @Column(name = "dt_criacao", updatable = false)
    private LocalDateTime dataCriacao;

}

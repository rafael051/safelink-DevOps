package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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
 * @author Rafael
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
    private Long id;

    // ===========================
    // 💬 Conteúdo do Relato
    // ===========================

    /**
     * Texto do relato descritivo.
     * Pode incluir percepção do risco, descrição de danos, etc.
     */
    @NotBlank(message = "A mensagem do relato é obrigatória.")
    private String mensagem;

    /**
     * Data e hora em que o relato foi feito.
     * Usado para rastrear o momento da observação do usuário.
     */
    @NotNull(message = "A data do relato é obrigatória.")
    private LocalDateTime dataRelato;

    // ===========================
    // 🔗 Relacionamentos
    // ===========================

    /**
     * Usuário que realizou o relato.
     * Obrigatoriamente vinculado a um `User` autenticado no sistema.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User usuario;

    /**
     * Região onde o relato foi feito ou observado.
     * Cada relato está vinculado a uma região cadastrada.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "regiao_id", nullable = false)
    private Regiao regiao;
}

package br.com.fiap.safelink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

/**
 * # 🗺️ Entidade: Regiao
 *
 * Representa uma área geográfica monitorada pelo sistema SafeLink.
 * Cada região pode estar associada a eventos naturais ocorridos,
 * alertas emitidos, previsões de risco e relatos de usuários.
 *
 * ---
 * ## 🔗 Relacionamentos
 * - 1:N com `EventoNatural`
 * - 1:N com `PrevisaoRisco`
 * - 1:N com `Alerta`
 * - 1:N com `RelatoUsuario`
 *
 * ---
 * ## 🎯 Finalidade
 * Permitir a identificação de zonas vulneráveis e a agregação de dados
 * meteorológicos e sociais para atuação rápida e estratégica.
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
@Table(name = "tb_regiao")
public class Regiao {

    // ===========================
    // 🔑 Identificação Geográfica
    // ===========================

    /** Identificador único da região (chave primária). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regiao")
    private Long id;

    /** Nome da região (ex: "Centro", "Zona Leste", etc). */
    @NotBlank(message = "O nome da região é obrigatório.")
    @Column(name = "nm_regiao", nullable = false)
    private String nome;

    /** Nome da cidade onde a região está localizada. */
    @NotBlank(message = "A cidade é obrigatória.")
    @Column(name = "nm_cidade", nullable = false)
    private String cidade;

    /** Estado da região (UF). */
    @NotBlank(message = "O estado é obrigatório.")
    @Column(name = "sg_estado", nullable = false, length = 2)
    private String estado;

    /** Latitude da coordenada central da região. */
    @NotNull(message = "Latitude é obrigatória.")
    @Column(name = "vl_latitude", nullable = false)
    private Double latitude;

    /** Longitude da coordenada central da região. */
    @NotNull(message = "Longitude é obrigatória.")
    @Column(name = "vl_longitude", nullable = false)
    private Double longitude;

    // ===========================
    // 🔗 Relacionamentos
    // ===========================

    /** Lista de eventos naturais registrados nesta região. */
    @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
    private List<EventoNatural> eventos;

    /** Lista de previsões de risco associadas a esta região. */
    @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
    private List<PrevisaoRisco> previsoes;

    /** Lista de alertas emitidos para esta região. */
    @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
    private List<Alerta> alertas;

    /** Lista de relatos de usuários associados a esta região. */
    @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL)
    private List<RelatoUsuario> relatos;
}

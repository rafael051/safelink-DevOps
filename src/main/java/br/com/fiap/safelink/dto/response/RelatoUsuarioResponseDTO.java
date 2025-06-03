package br.com.fiap.safelink.dto.response;

import lombok.*;
import java.time.LocalDateTime;

/**
 * # 📤 DTO: RelatoUsuarioResponseDTO
 *
 * Retorna os dados de um relato feito por um usuário do sistema SafeLink.
 * Inclui informações textuais do relato, data, e nomes dos envolvidos (usuário e região).
 *
 * ---
 * ## 📌 Utilização
 * - Utilizado nas respostas de endpoints GET e POST de relatos.
 * - Permite exibição clara e organizada de informações coletadas da comunidade.
 *
 * ---
 * @author Rafael
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatoUsuarioResponseDTO {

    // ===========================
    // 🔑 Identificação
    // ===========================

    /** Identificador único do relato realizado. */
    private Long id;

    // ===========================
    // 📝 Conteúdo do Relato
    // ===========================

    /** Texto livre enviado pelo usuário com a descrição do ocorrido. */
    private String mensagem;

    /** Data e hora em que o relato foi registrado. */
    private LocalDateTime dataRelato;

    // ===========================
    // 👤 Informações do Usuário
    // ===========================

    /** Nome do usuário que realizou o relato. */
    private String nomeUsuario;

    // ===========================
    // 📍 Informações da Região
    // ===========================

    /** Nome da região geográfica associada ao relato. */
    private String nomeRegiao;
}

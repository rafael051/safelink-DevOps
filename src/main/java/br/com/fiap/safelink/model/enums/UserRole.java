package br.com.fiap.safelink.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 🎭 Enumeração: UserRole
 *
 * Define os perfis (roles) de usuários da aplicação.
 * Usado para controle de permissões no sistema.
 */
@Schema(description = "Perfis de usuário disponíveis para controle de acesso.")
public enum UserRole {

    @Schema(description = "Administrador com permissões totais")
    ADMIN,

    @Schema(description = "Usuário comum com permissões limitadas")
    USER
}

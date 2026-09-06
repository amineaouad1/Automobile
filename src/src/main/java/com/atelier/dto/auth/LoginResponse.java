package com.atelier.dto.auth;

import com.atelier.entity.enums.RoleUtilisateur;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    @Schema(
            description = "Token JWT généré après une authentification réussie",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String token;

    @Schema(
            description = "Adresse e-mail de l'utilisateur authentifié",
            example = "manager@gx.com"
    )
    private String email;

    @Schema(
            description = "Rôle de l'utilisateur authentifié",
            example = "ROLE_MANAGER"
    )
    private RoleUtilisateur role;
}
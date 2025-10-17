package com.saki.gestion_stock.dto.auth;

import com.saki.gestion_stock.dto.UtilisateurDto;
import lombok.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
    private UtilisateurDto utilisateur;
}



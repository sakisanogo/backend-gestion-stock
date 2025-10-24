// Déclaration du package pour les DTOs d'authentification
package com.saki.gestion_stock.dto.auth;

// Importation du DTO Utilisateur
import com.saki.gestion_stock.dto.UtilisateurDto;
// Importations des annotations Lombok
import lombok.*;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
// Annotation Lombok pour générer un constructeur avec tous les arguments
@AllArgsConstructor
// Annotation Lombok pour générer un constructeur sans arguments
@NoArgsConstructor
public class AuthenticationResponse {
    // Champ pour stocker le token JWT d'accès
    private String accessToken;

    // Champ pour stocker les informations de l'utilisateur authentifié
    private UtilisateurDto utilisateur;
}
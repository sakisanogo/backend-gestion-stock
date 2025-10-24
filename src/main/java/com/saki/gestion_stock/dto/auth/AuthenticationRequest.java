// Déclaration du package pour les DTOs d'authentification
package com.saki.gestion_stock.dto.auth;

// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class AuthenticationRequest {

    // Champ pour stocker le login (email ou nom d'utilisateur)
    private String login;

    // Champ pour stocker le mot de passe
    private String password;

}
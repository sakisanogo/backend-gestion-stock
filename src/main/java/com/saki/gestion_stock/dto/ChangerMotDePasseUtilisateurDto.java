// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
public class ChangerMotDePasseUtilisateurDto {

    // Identifiant unique de l'utilisateur dont on veut changer le mot de passe
    private Integer id;

    // Nouveau mot de passe de l'utilisateur
    private String motDePasse;

    // Confirmation du nouveau mot de passe (doit être identique à motDePasse)
    private String confirmMotDePasse;

}
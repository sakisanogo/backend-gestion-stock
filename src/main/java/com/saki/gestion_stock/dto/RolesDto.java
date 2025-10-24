// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'annotation Jackson pour ignorer des champs lors de la sérialisation JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
// Importation des entités
import com.saki.gestion_stock.model.Roles;
import com.saki.gestion_stock.model.Utilisateur;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class RolesDto {

    // Identifiant unique du rôle
    private Integer id;

    // Nom du rôle (ex: ADMIN, USER, MANAGER, etc.)
    private String roleName;

    // Utilisateur associé à ce rôle
    // Annotation pour ignorer ce champ lors de la sérialisation JSON (évite les références circulaires)
    @JsonIgnore
    private UtilisateurDto utilisateur;

    // Méthode statique pour convertir une entité Roles en RolesDto
    public static RolesDto fromEntity(Roles roles) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (roles == null) {
            return null;
        }

        // Construction du RolesDto en utilisant le pattern Builder de Lombok
        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .build();
    }

    // Méthode statique pour convertir un RolesDto en entité Roles
    public static Roles toEntity(RolesDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Roles
        Roles roles = new Roles();
        roles.setId(dto.getId());
        roles.setRoleName(dto.getRoleName());
        // Conversion de l'UtilisateurDto en entité Utilisateur
        roles.setUtilisateur(UtilisateurDto.toEntity(dto.getUtilisateur()));

        return roles;
    }

}
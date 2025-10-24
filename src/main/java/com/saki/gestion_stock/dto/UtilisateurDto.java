// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importations des entités
import com.saki.gestion_stock.model.Entreprise;
import com.saki.gestion_stock.model.Roles;
import com.saki.gestion_stock.model.Utilisateur;
// Importations des classes Java
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class UtilisateurDto {

    // Identifiant unique de l'utilisateur
    private Integer id;

    // Nom de famille de l'utilisateur
    private String nom;

    // Prénom de l'utilisateur
    private String prenom;

    // Adresse email de l'utilisateur (utilisé pour l'authentification)
    private String email;

    // Date de naissance de l'utilisateur
    private Instant dateDeNaissance;

    // Mot de passe de l'utilisateur (devrait être hashé en pratique)
    private String moteDePasse;

    // Adresse de l'utilisateur
    private AdresseDto adresse;

    // Photo de profil de l'utilisateur
    private String photo;

    // Entreprise à laquelle appartient l'utilisateur
    private EntrepriseDto entreprise;

    // Liste des rôles et permissions de l'utilisateur
    private List<RolesDto> roles;

    // Méthode statique pour convertir une entité Utilisateur en UtilisateurDto
    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (utilisateur == null) {
            return null;
        }

        // Construction de l'UtilisateurDto en utilisant le pattern Builder de Lombok
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .moteDePasse(utilisateur.getMoteDePasse())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                // Conversion de l'adresse entité en AdresseDto
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                // Conversion de l'entreprise entité en EntrepriseDto
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                // Conversion des rôles entité en RolesDto
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    // Méthode statique pour convertir un UtilisateurDto en entité Utilisateur
    public static Utilisateur toEntity(UtilisateurDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(dto.getId());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setMoteDePasse(dto.getMoteDePasse());
        utilisateur.setDateDeNaissance(dto.getDateDeNaissance());
        // Conversion de l'AdresseDto en entité Adresse
        utilisateur.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        utilisateur.setPhoto(dto.getPhoto());
        // Conversion de l'EntrepriseDto en entité Entreprise
        utilisateur.setEntreprise(EntrepriseDto.toEntity(dto.getEntreprise()));

        return utilisateur;
    }
}
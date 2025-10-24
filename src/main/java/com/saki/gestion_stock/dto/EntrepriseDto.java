// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'annotation Jackson pour ignorer des champs lors de la sérialisation JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
// Importation des entités
import com.saki.gestion_stock.model.Adresse;
import com.saki.gestion_stock.model.Entreprise;
// Importation pour la liste d'utilisateurs
import java.util.List;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class EntrepriseDto {

    // Identifiant unique de l'entreprise
    private Integer id;

    // Nom de l'entreprise
    private String nom;

    // Description de l'entreprise
    private String description;

    // Adresse de l'entreprise
    private AdresseDto adresse;

    // Code fiscal de l'entreprise (SIRET, etc.)
    private String codeFiscal;

    // Logo ou photo de l'entreprise
    private String photo;

    // Adresse email de contact de l'entreprise
    private String email;

    // Mot de passe pour l'accès au système (devrait être hashé en pratique)
    private String motDePasse;

    // Numéro de téléphone de l'entreprise
    private String numTel;

    // Site web de l'entreprise
    private String steWeb;

    // Liste des utilisateurs appartenant à cette entreprise
    // Annotation pour ignorer ce champ lors de la sérialisation JSON (évite les références circulaires)
    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    // Méthode statique pour convertir une entité Entreprise en EntrepriseDto
    public static EntrepriseDto fromEntity(Entreprise entreprise) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (entreprise == null) {
            return null;
        }

        // Construction de l'EntrepriseDto en utilisant le pattern Builder de Lombok
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                // Conversion de l'adresse entité en AdresseDto
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .motDePasse(entreprise.getMotDePasse())
                .numTel(entreprise.getNumTel())
                .steWeb(entreprise.getSteWeb())
                .build();
    }

    // Méthode statique pour convertir un EntrepriseDto en entité Entreprise
    public static Entreprise toEntity(EntrepriseDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Entreprise
        Entreprise entreprise = new Entreprise();

        // Copie des données du DTO vers l'entité
        entreprise.setId(dto.getId());
        entreprise.setNom(dto.getNom());
        entreprise.setDescription(dto.getDescription());
        // Conversion de l'AdresseDto en entité Adresse
        entreprise.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        entreprise.setCodeFiscal(dto.getCodeFiscal());
        entreprise.setPhoto(dto.getPhoto());
        entreprise.setEmail(dto.getEmail());
        entreprise.setMotDePasse(dto.getMotDePasse());
        entreprise.setNumTel(dto.getNumTel());
        entreprise.setSteWeb(dto.getSteWeb());

        return entreprise;
    }

}
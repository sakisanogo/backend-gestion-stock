// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'annotation Jackson pour ignorer des champs lors de la sérialisation JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
// Importation des entités
import com.saki.gestion_stock.model.Adresse;
import com.saki.gestion_stock.model.CommandeFournisseur;
import com.saki.gestion_stock.model.Fournisseur;
// Importation pour la liste de commandes fournisseurs
import java.util.List;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class FournisseurDto {

    // Identifiant unique du fournisseur
    private Integer id;

    // Nom de famille du fournisseur
    private String nom;

    // Prénom du fournisseur
    private String prenom;

    // Adresse du fournisseur
    private AdresseDto adresse;

    // Photo ou logo du fournisseur
    private String photo;

    // Adresse email du fournisseur
    private String mail;

    // Numéro de téléphone du fournisseur
    private String numTel;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Liste des commandes passées à ce fournisseur
    // Annotation pour ignorer ce champ lors de la sérialisation JSON (évite les références circulaires)
    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;

    // Méthode statique pour convertir une entité Fournisseur en FournisseurDto
    public static FournisseurDto fromEntity(Fournisseur fournisseur) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (fournisseur == null) {
            return null;
        }

        // Construction du FournisseurDto en utilisant le pattern Builder de Lombok
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                // Conversion de l'adresse entité en AdresseDto
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .photo(fournisseur.getPhoto())
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getNumTel())
                .idEntreprise(fournisseur.getIdEntreprise())
                .build();
    }

    // Méthode statique pour convertir un FournisseurDto en entité Fournisseur
    public static Fournisseur toEntity(FournisseurDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Fournisseur
        Fournisseur fournisseur = new Fournisseur();

        // Copie des données du DTO vers l'entité
        fournisseur.setId(dto.getId());
        fournisseur.setNom(dto.getNom());
        fournisseur.setPrenom(dto.getPrenom());
        // Conversion de l'AdresseDto en entité Adresse
        fournisseur.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        fournisseur.setPhoto(dto.getPhoto());
        fournisseur.setMail(dto.getMail());
        fournisseur.setNumTel(dto.getNumTel());
        fournisseur.setIdEntreprise(dto.getIdEntreprise());

        return fournisseur;
    }
}
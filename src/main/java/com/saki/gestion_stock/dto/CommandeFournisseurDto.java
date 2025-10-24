// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importations des entités
import com.saki.gestion_stock.model.CommandeFournisseur;
import com.saki.gestion_stock.model.EtatCommande;
// Importations des classes Java
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

// Importations des annotations Lombok
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
// Annotation Lombok pour générer un constructeur avec tous les arguments
@AllArgsConstructor
// Annotation Lombok pour générer un constructeur sans arguments
@NoArgsConstructor
public class CommandeFournisseurDto {

    // Identifiant unique de la commande fournisseur
    private Integer id;

    // Code unique de la commande
    private String code;

    // Date de création de la commande
    private Instant dateCommande;

    // État actuel de la commande (EN_PREPARATION, VALIDEE, LIVREE, etc.)
    private EtatCommande etatCommande;

    // Fournisseur associé à cette commande
    private FournisseurDto fournisseur;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Liste des lignes de commande (articles commandés)
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    // Méthode pour l'affichage de liste (SANS les lignes pour éviter la récursion)
    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (commandeFournisseur == null) {
            return null;
        }

        // Construction du CommandeFournisseurDto SANS les lignes de commande (optimisation performance)
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                // Conversion du fournisseur entité en FournisseurDto
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .etatCommande(commandeFournisseur.getEtatCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .ligneCommandeFournisseurs(null) // ⚠️ NE PAS inclure les lignes dans la liste
                .build();
    }

    // Méthode séparée pour avoir les détails AVEC les lignes
    public static CommandeFournisseurDto fromEntityWithLignes(CommandeFournisseur commandeFournisseur) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (commandeFournisseur == null) {
            return null;
        }

        // Construction du CommandeFournisseurDto AVEC les lignes de commande (vue détaillée)
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                // Conversion du fournisseur entité en FournisseurDto
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .etatCommande(commandeFournisseur.getEtatCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                // Conversion des lignes de commande entité en LigneCommandeFournisseurDto
                .ligneCommandeFournisseurs(
                        commandeFournisseur.getLigneCommandeFournisseurs() != null ?
                                commandeFournisseur.getLigneCommandeFournisseurs().stream()
                                        .map(LigneCommandeFournisseurDto::fromEntity)
                                        .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    // Méthode statique pour convertir un CommandeFournisseurDto en entité CommandeFournisseur
    public static CommandeFournisseur toEntity(CommandeFournisseurDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité CommandeFournisseur
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(dto.getId());
        commandeFournisseur.setCode(dto.getCode());
        commandeFournisseur.setDateCommande(dto.getDateCommande());
        // Conversion du FournisseurDto en entité Fournisseur
        commandeFournisseur.setFournisseur(FournisseurDto.toEntity(dto.getFournisseur()));
        commandeFournisseur.setIdEntreprise(dto.getIdEntreprise());
        commandeFournisseur.setEtatCommande(dto.getEtatCommande());

        return commandeFournisseur;
    }

    // Méthode utilitaire pour vérifier si la commande est livrée
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
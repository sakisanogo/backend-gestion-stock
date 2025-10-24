// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité LigneCommandeFournisseur
import com.saki.gestion_stock.model.LigneCommandeFournisseur;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Importation de BigDecimal pour les calculs financiers
import java.math.BigDecimal;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
// Annotation Lombok pour générer un constructeur sans arguments
@NoArgsConstructor
// Annotation Lombok pour générer un constructeur avec tous les arguments
@AllArgsConstructor
public class LigneCommandeFournisseurDto {

    // Identifiant unique de la ligne de commande fournisseur
    private Integer id;

    // Article associé à cette ligne de commande
    private ArticleDto article;

    // Commande fournisseur associée
    private CommandeFournisseurDto commandeFournisseur;

    // Quantité commandée de l'article
    private BigDecimal quantite;

    // Prix unitaire de l'article au moment de la commande
    private BigDecimal prixUnitaire;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Méthode statique pour convertir une entité LigneCommandeFournisseur en LigneCommandeFournisseurDto
    public static LigneCommandeFournisseurDto fromEntity(LigneCommandeFournisseur ligneCommandeFournisseur) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (ligneCommandeFournisseur == null) {
            return null;
        }

        // Construction du LigneCommandeFournisseurDto en utilisant le pattern Builder de Lombok
        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                // Conversion de l'article entité en ArticleDto
                .article(ArticleDto.fromEntity(ligneCommandeFournisseur.getArticle()))
                // ✅ CORRECT : Évite la récursion en mettant null pour la commande
                .commandeFournisseur(null) // ✅ CORRECT : Évite la récursion
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                .build();
    }

    // Méthode alternative si vous avez besoin de la commande dans certains cas
    public static LigneCommandeFournisseurDto fromEntityWithCommande(LigneCommandeFournisseur ligneCommandeFournisseur) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (ligneCommandeFournisseur == null) {
            return null;
        }

        // Appel de la méthode de base pour créer le DTO
        LigneCommandeFournisseurDto dto = fromEntity(ligneCommandeFournisseur);

        // Inclure la commande mais sans ses lignes pour éviter la récursion
        if (ligneCommandeFournisseur.getCommandeFournisseur() != null) {
            // Utilisation de fromEntity() (sans lignes) pour éviter la récursion infinie
            dto.setCommandeFournisseur(CommandeFournisseurDto.fromEntity(ligneCommandeFournisseur.getCommandeFournisseur()));
        }
        return dto;
    }

    // Méthode statique pour convertir un LigneCommandeFournisseurDto en entité LigneCommandeFournisseur
    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité LigneCommandeFournisseur
        LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur();
        ligneCommandeFournisseur.setId(dto.getId());
        // Conversion de l'ArticleDto en entité Article
        ligneCommandeFournisseur.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneCommandeFournisseur.setPrixUnitaire(dto.getPrixUnitaire());
        ligneCommandeFournisseur.setQuantite(dto.getQuantite());
        ligneCommandeFournisseur.setIdEntreprise(dto.getIdEntreprise());

        return ligneCommandeFournisseur;
    }
}
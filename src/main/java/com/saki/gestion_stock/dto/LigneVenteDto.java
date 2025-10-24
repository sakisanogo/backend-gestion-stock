// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importations des entités
import com.saki.gestion_stock.model.Article;
import com.saki.gestion_stock.model.LigneVente;
import com.saki.gestion_stock.model.Ventes;
// Importation de BigDecimal pour les calculs financiers
import java.math.BigDecimal;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class LigneVenteDto {

    // Identifiant unique de la ligne de vente
    private Integer id;

    // Vente associée à cette ligne
    private VentesDto vente;

    // Article vendu dans cette ligne
    private ArticleDto article;

    // Quantité vendue de l'article
    private BigDecimal quantite;

    // Prix unitaire de l'article au moment de la vente
    private BigDecimal prixUnitaire;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Méthode statique pour convertir une entité LigneVente en LigneVenteDto
    public static LigneVenteDto fromEntity(LigneVente ligneVente) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (ligneVente == null) {
            return null;
        }

        // Construction du LigneVenteDto en utilisant le pattern Builder de Lombok
        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                // Conversion de la vente entité en VentesDto
                .vente(VentesDto.fromEntity(ligneVente.getVente()))
                // Conversion de l'article entité en ArticleDto
                .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .idEntreprise(ligneVente.getIdEntreprise())
                .build();
    }

    // Méthode statique pour convertir un LigneVenteDto en entité LigneVente
    public static LigneVente toEntity(LigneVenteDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité LigneVente
        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(dto.getId());
        // Conversion du VentesDto en entité Ventes
        ligneVente.setVente(VentesDto.toEntity(dto.getVente()));
        // Conversion de l'ArticleDto en entité Article
        ligneVente.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneVente.setQuantite(dto.getQuantite());
        ligneVente.setPrixUnitaire(dto.getPrixUnitaire());
        ligneVente.setIdEntreprise(dto.getIdEntreprise());

        return ligneVente;
    }

}
// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité LigneCommandeClient
import com.saki.gestion_stock.model.LigneCommandeClient;
// Importation de BigDecimal pour les calculs financiers
import java.math.BigDecimal;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class LigneCommandeClientDto {

    // Identifiant unique de la ligne de commande client
    private Integer id;

    // Article associé à cette ligne de commande
    private ArticleDto article;

    // Commande client associée (gardé pour la sérialisation mais utilisé avec précaution)
    private CommandeClientDto commandeClient; // Gardé pour la sérialisation

    // Quantité commandée de l'article
    private BigDecimal quantite;

    // Prix unitaire de l'article au moment de la commande
    private BigDecimal prixUnitaire;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Méthode statique pour convertir une entité LigneCommandeClient en LigneCommandeClientDto
    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (ligneCommandeClient == null) {
            return null;
        }

        // Construction du LigneCommandeClientDto en utilisant le pattern Builder de Lombok
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                // Conversion de l'article entité en ArticleDto
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                // Note: On ne sérialise pas commandeClient pour éviter la récursion
                .build();
    }

    // Méthode statique pour convertir un LigneCommandeClientDto en entité LigneCommandeClient
    public static LigneCommandeClient toEntity(LigneCommandeClientDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité LigneCommandeClient
        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(dto.getId());
        // Conversion de l'ArticleDto en entité Article
        ligneCommandeClient.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneCommandeClient.setPrixUnitaire(dto.getPrixUnitaire());
        ligneCommandeClient.setQuantite(dto.getQuantite());
        ligneCommandeClient.setIdEntreprise(dto.getIdEntreprise());
        // Note: commandeClient sera défini dans CommandeClientDto.toEntity()
        return ligneCommandeClient;
    }
}
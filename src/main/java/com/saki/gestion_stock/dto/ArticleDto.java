// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité Article
import com.saki.gestion_stock.model.Article;
// Importation de la classe BigDecimal pour les calculs financiers
import java.math.BigDecimal;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
public class ArticleDto {

    // Identifiant unique de l'article
    private Integer id;

    // Code unique de l'article (référence)
    private String codeArticle;

    // Désignation ou nom de l'article
    private String designation;

    // Prix unitaire hors taxes
    private BigDecimal prixUnitaireHt;

    // Taux de TVA applicable
    private BigDecimal tauxTva;

    // Prix unitaire toutes taxes comprises (calculé)
    private BigDecimal prixUnitaireTtc;

    // Chemin ou URL de la photo de l'article
    private String photo;

    // Catégorie à laquelle appartient l'article
    private CategoryDto category;

    // Identifiant de l'entreprise propriétaire de l'article (pour le multi-tenant)
    private Integer idEntreprise;

    // Méthode statique pour convertir une entité Article en ArticleDto
    public static ArticleDto fromEntity(Article article) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (article == null) {
            return null;
        }

        // Construction de l'ArticleDto en utilisant le pattern Builder de Lombok
        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .photo(article.getPhoto())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .tauxTva(article.getTauxTva())
                .idEntreprise(article.getIdEntreprise())
                // Conversion de la catégorie entité en CategoryDto
                .category(CategoryDto.fromEntity(article.getCategory()))
                .build();
    }

    // Méthode statique pour convertir un ArticleDto en entité Article
    public static Article toEntity(ArticleDto articleDto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (articleDto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Article
        Article article = new Article();

        // Copie des données du DTO vers l'entité
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPhoto(articleDto.getPhoto());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setTauxTva(articleDto.getTauxTva());
        article.setIdEntreprise(articleDto.getIdEntreprise());
        // Conversion de la CategoryDto en entité Category
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));

        return article;
    }

}
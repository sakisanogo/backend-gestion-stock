// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité Category
import com.saki.gestion_stock.model.Category;
// Importation de l'annotation Jackson pour ignorer des champs lors de la sérialisation JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
// Importation pour la liste d'articles
import java.util.List;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class CategoryDto {

    // Identifiant unique de la catégorie
    private Integer id;

    // Code unique de la catégorie
    private String code;

    // Désignation ou nom de la catégorie
    private String designation;

    // Identifiant de l'entreprise propriétaire de la catégorie (pour le multi-tenant)
    private Integer idEntreprise;

    // Liste des articles appartenant à cette catégorie
    // Annotation pour ignorer ce champ lors de la sérialisation JSON (évite les références circulaires)
    @JsonIgnore
    private List<ArticleDto> articles;

    // Méthode statique pour convertir une entité Category en CategoryDto
    public static CategoryDto fromEntity(Category category) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (category == null) {
            return null;
            // TODO throw an exception - commentaire pour amélioration future
        }

        // Construction du CategoryDto en utilisant le pattern Builder de Lombok
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();
    }

    // Méthode statique pour convertir un CategoryDto en entité Category
    public static Category toEntity(CategoryDto categoryDto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (categoryDto == null) {
            return null;
            // TODO throw an exception - commentaire pour amélioration future
        }

        // Création d'une nouvelle instance d'entité Category
        Category category = new Category();

        // Copie des données du DTO vers l'entité
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());

        return category;
    }
}
package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.ArticleDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets ArticleDto
// Contient la logique de validation métier pour les articles
public class ArticleValidator {

    /**
     * Valide un objet ArticleDto et retourne la liste des erreurs
     *
     * @param dto L'objet article à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(ArticleDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet article n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner la designation de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT l'article");
            errors.add("Veuillez renseigner le taux TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez selectionner une categorie");
            return errors;
        }

        // Validation du code article (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getCodeArticle())) {
            errors.add("Veuillez renseigner le code de l'article");
        }

        // Validation de la désignation (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getDesignation())) {
            errors.add("Veuillez renseigner la designation de l'article");
        }

        // Validation du prix unitaire HT (obligatoire)
        if (dto.getPrixUnitaireHt() == null) {
            errors.add("Veuillez renseigner le prix unitaire HT l'article");
        }

        // Validation du taux de TVA (obligatoire)
        if (dto.getTauxTva() == null) {
            errors.add("Veuillez renseigner le taux TVA de l'article");
        }

        // Validation du prix unitaire TTC (obligatoire)
        if (dto.getPrixUnitaireTtc() == null) {
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }

        // Validation de la catégorie (obligatoire avec ID valide)
        if (dto.getCategory() == null || dto.getCategory().getId() == null) {
            errors.add("Veuillez selectionner une categorie");
        }

        return errors;
    }
}
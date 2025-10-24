package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets CategoryDto
// Contient la logique de validation métier pour les catégories
public class CategoryValidator {

    /**
     * Valide un objet CategoryDto et retourne la liste des erreurs
     *
     * @param categoryDto L'objet catégorie à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>();

        // Validation du code de la catégorie (champ obligatoire)
        // Vérifie que l'objet n'est pas null ET que le code n'est pas vide
        if (categoryDto == null || !StringUtils.hasLength(categoryDto.getCode())) {
            errors.add("Veuillez renseigner le code de la categorie");
        }

        return errors;
    }
}
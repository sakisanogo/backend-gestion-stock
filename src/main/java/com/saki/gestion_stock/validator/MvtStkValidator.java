package com.saki.gestion_stock.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.saki.gestion_stock.dto.MvtStkDto;
import org.springframework.util.StringUtils;

// Validateur pour les objets MvtStkDto
// Contient la logique de validation métier pour les mouvements de stock
public class MvtStkValidator {

    /**
     * Valide un objet MvtStkDto et retourne la liste des erreurs
     *
     * @param dto L'objet mouvement de stock à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(MvtStkDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet mouvement de stock n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
            errors.add("Veuillez renseigner la quantite du mouvenent");
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner le type du mouvement");
            return errors;
        }

        // Validation de la date du mouvement (obligatoire)
        if (dto.getDateMvt() == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
        }

        // Validation de la quantité (obligatoire et différente de zéro)
        // Compare à BigDecimal.ZERO pour éviter les quantités nulles
        if (dto.getQuantite() == null || dto.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantite du mouvenent");
        }

        // Validation de l'article (obligatoire avec ID valide)
        if (dto.getArticle() == null || dto.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article");
        }

        // Validation du type de mouvement (obligatoire)
        // Utilise .name() pour convertir l'énumération en String
        if (!StringUtils.hasLength(dto.getTypeMvt().name())) {
            errors.add("Veuillez renseigner le type du mouvement");
        }

        return errors;
    }
}
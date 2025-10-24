package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.AdresseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets AdresseDto
// Contient la logique de validation métier pour les adresses
public class AdresseValidator {

    /**
     * Valide un objet AdresseDto et retourne la liste des erreurs
     *
     * @param adresseDto L'objet adresse à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(AdresseDto adresseDto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet adresse n'est pas null
        if (adresseDto == null) {
            errors.add("Veuillez renseigner l'adresse 1'");
            errors.add("Veuillez renseigner la ville'");
            errors.add("Veuillez renseigner le pays'");
            errors.add("Veuillez renseigner le code postal'");
            return errors;
        }

        // Validation de l'adresse principale (obligatoire)
        if (!StringUtils.hasLength(adresseDto.getAdresse1())) {
            errors.add("Veuillez renseigner l'adresse 1'");
        }

        // Validation de la ville (obligatoire)
        if (!StringUtils.hasLength(adresseDto.getVille())) {
            errors.add("Veuillez renseigner la ville'");
        }

        // Validation du pays (obligatoire)
        if (!StringUtils.hasLength(adresseDto.getPays())) {
            errors.add("Veuillez renseigner le pays'");
        }

        // NOTE: Il y a une incohérence ici - on vérifie adresse1 au lieu de codePostale
        // Cette ligne devrait probablement vérifier getCodePostale()
        if (!StringUtils.hasLength(adresseDto.getAdresse1())) {
            errors.add("Veuillez renseigner le code postal'");
        }

        return errors;
    }
}
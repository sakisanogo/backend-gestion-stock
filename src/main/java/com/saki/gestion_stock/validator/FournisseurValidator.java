package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets FournisseurDto
// Contient la logique de validation métier pour les fournisseurs
public class FournisseurValidator {

    /**
     * Valide un objet FournisseurDto et retourne la liste des erreurs
     *
     * @param dto L'objet fournisseur à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(FournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet fournisseur n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner le nom du fournisseur");
            errors.add("Veuillez renseigner le prenom du fournisseur");
            errors.add("Veuillez renseigner le Mail du fournisseur");
            errors.add("Veuillez renseigner le numero de telephone du fournisseur");
            // Valide une adresse null pour récupérer toutes les erreurs d'adresse
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        // Validation du nom (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom du fournisseur");
        }

        // Validation du prénom (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getPrenom())) {
            errors.add("Veuillez renseigner le prenom du fournisseur");
        }

        // Validation de l'email (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getMail())) {
            errors.add("Veuillez renseigner le Mail du fournisseur");
        }

        // Validation du numéro de téléphone (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numero de telephone du fournisseur");
        }

        // Validation de l'adresse en réutilisant AdresseValidator
        // L'adresse est obligatoire pour un fournisseur
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));

        return errors;
    }
}
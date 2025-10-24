package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.EntrepriseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets EntrepriseDto
// Contient la logique de validation métier pour les entreprises
public class EntrepriseValidator {

    /**
     * Valide un objet EntrepriseDto et retourne la liste des erreurs
     *
     * @param dto L'objet entreprise à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(EntrepriseDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet entreprise n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez reseigner la description de l'entreprise");
            errors.add("Veuillez reseigner le code fiscal de l'entreprise");
            errors.add("Veuillez reseigner l'email de l'entreprise");
            errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
            // Valide une adresse null pour récupérer toutes les erreurs d'adresse
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        // Validation du nom de l'entreprise (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
        }

        // Validation de la description (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getDescription())) {
            errors.add("Veuillez reseigner la description de l'entreprise");
        }

        // Validation du code fiscal (obligatoire et non vide)
        // Ex: SIRET, numéro TVA, ou autre identifiant fiscal
        if (!StringUtils.hasLength(dto.getCodeFiscal())) {
            errors.add("Veuillez reseigner le code fiscal de l'entreprise");
        }

        // Validation de l'email (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez reseigner l'email de l'entreprise");
        }

        // Validation du numéro de téléphone (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
        }

        // Validation de l'adresse en réutilisant AdresseValidator
        // L'adresse est obligatoire pour une entreprise
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));

        return errors;
    }
}
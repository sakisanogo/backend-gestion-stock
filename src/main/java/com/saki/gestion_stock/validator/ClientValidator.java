package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets ClientDto
// Contient la logique de validation métier pour les clients
public class ClientValidator {

    /**
     * Valide un objet ClientDto et retourne la liste des erreurs
     *
     * @param dto L'objet client à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(ClientDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet client n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner le prenom du client");
            errors.add("Veuillez renseigner le Mail du client");
            errors.add("Veuillez renseigner le numero de telephone du client");
            // Valide une adresse null pour récupérer toutes les erreurs d'adresse
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        // Validation du nom (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getNom())) {
            errors.add("Veuillez renseigner le nom du client");
        }

        // Validation du prénom (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getPrenom())) {
            errors.add("Veuillez renseigner le prenom du client");
        }

        // Validation de l'email (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getMail())) {
            errors.add("Veuillez renseigner le Mail du client");
        }

        // Validation du numéro de téléphone (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getNumTel())) {
            errors.add("Veuillez renseigner le numero de telephone du client");
        }

        // Validation de l'adresse en réutilisant AdresseValidator
        // L'adresse est obligatoire pour un client
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));

        return errors;
    }
}
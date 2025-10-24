// Déclaration du package pour organiser les classes
package com.saki.gestion_stock.validator;

// Import des classes nécessaires
import com.saki.gestion_stock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de validation pour les objets UtilisateurDto
 * Contient des méthodes statiques pour valider les données utilisateur
 */
public class UtilisateurValidator {

    /**
     * Méthode de validation principale pour un UtilisateurDto
     * Vérifie que toutes les données obligatoires sont renseignées correctement
     *
     * @param utilisateurDto l'objet utilisateur à valider
     * @return une liste contenant tous les messages d'erreur de validation
     *         retourne une liste vide si aucune erreur n'est trouvée
     */
    public static List<String> validate(UtilisateurDto utilisateurDto) {
        // Création d'une liste pour accumuler les messages d'erreur
        List<String> errors = new ArrayList<>();

        // Vérification si l'objet utilisateur est null
        if (utilisateurDto == null) {
            // Ajout de tous les messages d'erreur obligatoires pour un objet null
            errors.add("Veuillez renseigner le nom d'utilisateur");
            errors.add("Veuillez renseigner le prenom d'utilisateur");
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
            errors.add("Veuillez renseigner l'adresse d'utilisateur");
            // Validation de l'adresse (qui sera null) et ajout des erreurs correspondantes
            errors.addAll(AdresseValidator.validate(null));
            // Retour immédiat car l'objet est null
            return errors;
        }

        // Validation du champ nom : vérifie qu'il n'est pas null, vide ou contenant seulement des espaces
        if (!StringUtils.hasLength(utilisateurDto.getNom())) {
            errors.add("Veuillez renseigner le nom d'utilisateur");
        }

        // Validation du champ prénom
        if (!StringUtils.hasLength(utilisateurDto.getPrenom())) {
            errors.add("Veuillez renseigner le prenom d'utilisateur");
        }

        // Validation du champ email
        if (!StringUtils.hasLength(utilisateurDto.getEmail())) {
            errors.add("Veuillez renseigner l'email d'utilisateur");
        }

        // Validation du champ mot de passe
        if (!StringUtils.hasLength(utilisateurDto.getMoteDePasse())) {
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
        }

        // Validation de la date de naissance (doit être non null)
        if (utilisateurDto.getDateDeNaissance() == null) {
            errors.add("Veuillez renseigner la date de naissance d'utilisateur");
        }

        // Validation de l'adresse en utilisant le validateur dédié AdresseValidator
        // et ajout de toutes les erreurs d'adresse à la liste principale
        errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));

        // Retour de la liste complète des erreurs (peut être vide si aucune erreur)
        return errors;
    }

}
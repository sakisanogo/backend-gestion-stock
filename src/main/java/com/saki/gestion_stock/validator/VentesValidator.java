// Déclaration du package pour organiser les classes
package com.saki.gestion_stock.validator;

// Import des classes nécessaires
import com.saki.gestion_stock.dto.VentesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de validation pour les objets VentesDto
 * Contient des méthodes statiques pour valider les données de vente
 */
public class VentesValidator {

    /**
     * Méthode de validation principale pour un VentesDto
     * Vérifie que toutes les données obligatoires d'une vente sont renseignées correctement
     *
     * @param dto l'objet vente à valider
     * @return une liste contenant tous les messages d'erreur de validation
     *         retourne une liste vide si aucune erreur n'est trouvée
     */
    public static List<String> validate(VentesDto dto) {
        // Création d'une liste pour accumuler les messages d'erreur
        List<String> errors = new ArrayList<>();

        // Vérification si l'objet vente est null
        if (dto == null) {
            // Ajout des messages d'erreur obligatoires pour un objet null
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            // Retour immédiat car l'objet est null
            return errors;
        }

        // Validation du champ code : vérifie qu'il n'est pas null, vide ou contenant seulement des espaces
        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }

        // Validation de la date de vente : vérifie que la date n'est pas null
        if (dto.getDateVente() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }

        // Retour de la liste complète des erreurs (peut être vide si aucune erreur)
        return errors;
    }

}
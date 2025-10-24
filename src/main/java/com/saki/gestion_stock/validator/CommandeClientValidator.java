package com.saki.gestion_stock.validator;

import java.util.ArrayList;
import java.util.List;

import com.saki.gestion_stock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

// Validateur pour les objets CommandeClientDto
// Contient la logique de validation métier pour les commandes clients
public class CommandeClientValidator {

    /**
     * Valide un objet CommandeClientDto et retourne la liste des erreurs
     *
     * @param dto L'objet commande client à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(CommandeClientDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet commande client n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        // Validation du code de commande (obligatoire et non vide)
        if (!StringUtils.hasLength(dto.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }

        // Validation de la date de commande (obligatoire)
        if (dto.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }

        // Validation de l'état de commande (obligatoire)
        // Convertit l'énumération en String pour vérifier qu'elle n'est pas vide
        if (!StringUtils.hasLength(dto.getEtatCommande().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }

        // Validation du client (obligatoire avec ID valide)
        if (dto.getClient() == null || dto.getClient().getId() == null) {
            errors.add("Veuillez renseigner le client");
        }

        return errors;
    }
}
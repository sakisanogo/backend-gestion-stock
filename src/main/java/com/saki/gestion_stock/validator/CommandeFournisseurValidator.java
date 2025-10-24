package com.saki.gestion_stock.validator;

import java.util.ArrayList;
import java.util.List;

import com.saki.gestion_stock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

// Validateur pour les objets CommandeFournisseurDto
// Contient la logique de validation métier pour les commandes fournisseurs
public class CommandeFournisseurValidator {

    /**
     * Valide un objet CommandeFournisseurDto et retourne la liste des erreurs
     *
     * @param dto L'objet commande fournisseur à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     */
    public static List<String> validate(CommandeFournisseurDto dto) {
        List<String> errors = new ArrayList<>();

        // Vérification que l'objet commande fournisseur n'est pas null
        if (dto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le fournisseur"); // ← CORRIGÉ : "client" → "fournisseur"
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
        // CORRECTION : Vérification directe de null pour éviter NullPointerException
        if (dto.getEtatCommande() == null) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }

        // Validation du fournisseur (obligatoire avec ID valide)
        if (dto.getFournisseur() == null || dto.getFournisseur().getId() == null) {
            errors.add("Veuillez renseigner le fournisseur");
        }

        return errors;
    }
}
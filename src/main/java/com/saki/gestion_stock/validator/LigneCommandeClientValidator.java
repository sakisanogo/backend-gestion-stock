package com.saki.gestion_stock.validator;

import com.saki.gestion_stock.dto.LigneCommandeClientDto;

import java.util.ArrayList;
import java.util.List;

// Validateur pour les objets LigneCommandeClientDto
// Contient la logique de validation métier pour les lignes de commande client
// TODO: À implémenter - Validateur actuellement vide
public class LigneCommandeClientValidator {

    /**
     * Valide un objet LigneCommandeClientDto et retourne la liste des erreurs
     *
     * @param dto L'objet ligne de commande client à valider
     * @return List<String> Liste des messages d'erreur, vide si aucune erreur
     *
     * TODO: À implémenter - Retourne actuellement une liste vide
     */
    public static List<String> validate(LigneCommandeClientDto dto) {
        List<String> errors = new ArrayList<>();

        // TODO: Implémenter la validation des champs obligatoires :
        // - Article (non null avec ID valide)
        // - Quantité (non null, positive, format décimal valide)
        // - Prix unitaire (non null, positif, format décimal valide)
        // - Vérification de la cohérence des données
        // - Vérification du stock disponible si nécessaire

        return errors; // Retourne toujours une liste vide pour le moment
    }
}
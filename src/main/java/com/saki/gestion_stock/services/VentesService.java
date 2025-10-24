package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.VentesDto;
import java.util.List;

// Interface de service pour la gestion des ventes
// Définit le contrat métier pour le processus commercial de vente
public interface VentesService {

    // Création d'une nouvelle vente avec validation complète
    // Inclut la sauvegarde des lignes de vente et la mise à jour automatique des stocks
    // Génère des mouvements de stock de sortie pour chaque article vendu
    VentesDto save(VentesDto dto);

    // Recherche d'une vente par son identifiant unique
    // Retourne la vente avec le détail de toutes ses lignes de vente
    VentesDto findById(Integer id);

    // Recherche d'une vente par son code (numéro de facture ou de vente)
    // Alternative pour les références métier et la recherche par les utilisateurs
    VentesDto findByCode(String code);

    // Récupération de toutes les ventes du système
    // Peut inclure des filtres par date, client ou statut dans l'implémentation
    List<VentesDto> findAll();

    // Suppression d'une vente avec gestion des dépendances
    // Doit vérifier qu'aucune ligne de vente n'est associée ou implémenter
    // une suppression en cascade avec ajustement des stocks
    void delete(Integer id);

}
package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.FournisseurDto;

import java.util.List;

// Interface de service pour la gestion des fournisseurs
// Définit le contrat métier pour la gestion des partenaires d'approvisionnement
public interface FournisseurService {

    // Sauvegarde d'un nouveau fournisseur ou mise à jour d'un fournisseur existant
    // Inclut la validation des données et la vérification des doublons
    FournisseurDto save(FournisseurDto dto);

    // Recherche d'un fournisseur par son identifiant unique
    // Retourne un FournisseurDto complet ou lance une exception si non trouvé
    FournisseurDto findById(Integer id);

    // Récupération de tous les fournisseurs du système
    // Retourne une liste pouvant être vide si aucun fournisseur n'existe
    List<FournisseurDto> findAll();

    // Suppression d'un fournisseur avec gestion des dépendances
    // Doit vérifier qu'aucune commande fournisseur n'est associée à ce fournisseur
    // ou implémenter une suppression en cascade des commandes associées
    void delete(Integer id);

}
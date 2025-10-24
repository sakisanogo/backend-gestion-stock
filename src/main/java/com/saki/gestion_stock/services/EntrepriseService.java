package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.EntrepriseDto;

import java.util.List;

// Interface de service pour la gestion des entreprises
// Définit le contrat métier pour la gestion multi-locataire du système
public interface EntrepriseService {

    // Création d'une nouvelle entreprise avec validation complète
    // Inclut généralement la création automatique d'un utilisateur administrateur
    // et la configuration initiale de l'environnement multi-locataire
    EntrepriseDto save(EntrepriseDto dto);

    // Recherche d'une entreprise par son identifiant unique
    // Retourne les informations complètes de l'entreprise
    EntrepriseDto findById(Integer id);

    // Récupération de toutes les entreprises du système
    // Principalement utilisée par les administrateurs système
    // Dans un contexte multi-locataire, l'accès peut être restreint
    List<EntrepriseDto> findAll();

    // Suppression d'une entreprise et de toutes ses données associées
    // Doit implémenter une suppression en cascade des utilisateurs, articles, commandes, etc.
    // ou une désactivation plutôt qu'une suppression physique
    void delete(Integer id);

}
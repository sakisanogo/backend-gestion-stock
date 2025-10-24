package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.ClientDto;

import java.util.List;

// Interface de service pour la gestion des clients
// Définit le contrat métier pour toutes les opérations liées aux clients
public interface ClientService {

    // Sauvegarde d'un nouveau client ou mise à jour d'un client existant
    // Inclut la validation des données et l'unicité des informations
    ClientDto save(ClientDto dto);

    // Recherche d'un client par son identifiant unique
    // Retourne un ClientDto complet ou lance une exception si non trouvé
    ClientDto findById(Integer id);

    // Récupération de tous les clients du système
    // Retourne une liste pouvant être vide si aucun client n'existe
    List<ClientDto> findAll();

    // Suppression d'un client avec gestion des dépendances
    // Doit vérifier qu'aucune commande client n'est associée à ce client
    // ou implémenter une suppression en cascade des commandes associées
    void delete(Integer id);

}
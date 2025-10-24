package com.saki.gestion_stock.repository;

import java.util.List;
import java.util.Optional;

import com.saki.gestion_stock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité CommandeClient, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {

    // Recherche une commande client par son code (généralement unique - numéro de commande)
    // Retourne un Optional<CommandeClient> pour gérer le cas où aucune commande n'est trouvée
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM commandeclient WHERE code = ?
    Optional<CommandeClient> findCommandeClientByCode(String code);

    // Recherche toutes les commandes clients associées à un client spécifique
    // Utilise l'ID du client comme critère de recherche via la relation ManyToOne
    // Retourne une liste de commandes (peut être vide si le client n'a aucune commande)
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête avec la jointure
    // La requête équivalente serait : SELECT * FROM commandeclient WHERE idclient = ?
    List<CommandeClient> findAllByClientId(Integer id);
}
package com.saki.gestion_stock.repository;

import com.saki.gestion_stock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité Client, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement toutes les méthodes de base sans code supplémentaire
// Cette interface hérite automatiquement des méthodes standards : save, findAll, findById, delete, etc.
public interface ClientRepository extends JpaRepository<Client, Integer> {

    // Aucune méthode personnalisée définie - utilisation exclusive des méthodes fournies par JpaRepository
    // Spring Data JPA fournit automatiquement les opérations CRUD complètes :
    // - save(S entity) : sauvegarde un client
    // - findAll() : récupère tous les clients
    // - findById(ID id) : recherche un client par son ID
    // - deleteById(ID id) : supprime un client par son ID
    // - count() : compte le nombre total de clients
    // - existsById(ID id) : vérifie si un client existe par son ID
    // ... et bien d'autres méthodes standards
}
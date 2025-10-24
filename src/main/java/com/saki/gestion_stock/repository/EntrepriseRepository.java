package com.saki.gestion_stock.repository;

import com.saki.gestion_stock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité Entreprise, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement toutes les méthodes de base sans code supplémentaire
// Cette interface hérite automatiquement des méthodes standards : save, findAll, findById, delete, etc.
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    // Aucune méthode personnalisée définie - utilisation exclusive des méthodes fournies par JpaRepository
    // Spring Data JPA fournit automatiquement les opérations CRUD complètes :
    // - save(S entity) : sauvegarde une entreprise
    // - findAll() : récupère toutes les entreprises
    // - findById(ID id) : recherche une entreprise par son ID
    // - deleteById(ID id) : supprime une entreprise par son ID
    // - count() : compte le nombre total d'entreprises
    // - existsById(ID id) : vérifie si une entreprise existe par son ID
    // ... et bien d'autres méthodes standards
    //
    // Dans un contexte multi-locataire, ce repository gère les entreprises elles-mêmes
    // et non les données métier qui sont filtrées par l'intercepteur avec idEntreprise
}
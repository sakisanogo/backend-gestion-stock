package com.saki.gestion_stock.repository;

import com.saki.gestion_stock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité Fournisseur, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement toutes les méthodes de base sans code supplémentaire
// Cette interface hérite automatiquement des méthodes standards : save, findAll, findById, delete, etc.
public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

    // Aucune méthode personnalisée définie - utilisation exclusive des méthodes fournies par JpaRepository
    // Spring Data JPA fournit automatiquement les opérations CRUD complètes :
    // - save(S entity) : sauvegarde un fournisseur
    // - findAll() : récupère tous les fournisseurs
    // - findById(ID id) : recherche un fournisseur par son ID
    // - deleteById(ID id) : supprime un fournisseur par son ID
    // - count() : compte le nombre total de fournisseurs
    // - existsById(ID id) : vérifie si un fournisseur existe par son ID
    // ... et bien d'autres méthodes standards
    //
    // Les fournisseurs sont automatiquement filtrés par l'intercepteur avec idEntreprise
    // dans le contexte multi-locataire, chaque entreprise ne voit que ses propres fournisseurs
}
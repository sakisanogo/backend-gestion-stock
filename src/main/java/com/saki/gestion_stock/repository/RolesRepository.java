package com.saki.gestion_stock.repository;

import com.saki.gestion_stock.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité Roles, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement toutes les méthodes de base sans code supplémentaire
// Cette interface hérite automatiquement des méthodes standards : save, findAll, findById, delete, etc.
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    // Aucune méthode personnalisée définie - utilisation exclusive des méthodes fournies par JpaRepository
    // Spring Data JPA fournit automatiquement les opérations CRUD complètes :
    // - save(S entity) : sauvegarde un rôle
    // - findAll() : récupère tous les rôles
    // - findById(ID id) : recherche un rôle par son ID
    // - deleteById(ID id) : supprime un rôle par son ID
    // - count() : compte le nombre total de rôles
    // - existsById(ID id) : vérifie si un rôle existe par son ID
    // ... et bien d'autres méthodes standards
    //
    // Les rôles sont gérés dans le cadre du système de sécurité Spring Security
    // Ce repository permet de gérer les définitions de rôles (ADMIN, USER, etc.) et leurs associations aux utilisateurs
    // Les rôles sont automatiquement filtrés par l'intercepteur avec idEntreprise dans le contexte multi-locataire
}
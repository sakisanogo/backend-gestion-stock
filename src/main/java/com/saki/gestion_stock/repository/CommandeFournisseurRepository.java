package com.saki.gestion_stock.repository;

import java.util.List;
import java.util.Optional;

import com.saki.gestion_stock.model.CommandeClient;
import com.saki.gestion_stock.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité CommandeFournisseur, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {

    // Recherche une commande fournisseur par son code (généralement unique - numéro de commande)
    // Retourne un Optional<CommandeFournisseur> pour gérer le cas où aucune commande n'est trouvée
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM commandefournisseur WHERE code = ?
    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    // ATTENTION : Il y a une incohérence dans le type de retour
    // La méthode devrait retourner List<CommandeFournisseur> mais retourne List<CommandeClient>
    // Recherche toutes les commandes fournisseurs associées à un fournisseur spécifique
    // Utilise l'ID du fournisseur comme critère de recherche via la relation ManyToOne
    // Retourne une liste de commandes (peut être vide si le fournisseur n'a aucune commande)
    List<CommandeClient> findAllByFournisseurId(Integer id);

    // Vérifie si un fournisseur a au moins une commande fournisseur
    // Utilise l'ID du fournisseur comme critère de recherche
    // Retourne true si le fournisseur a des commandes, false sinon
    // Utile pour vérifier les contraintes avant suppression d'un fournisseur
    boolean existsByFournisseurId(Integer id);
}
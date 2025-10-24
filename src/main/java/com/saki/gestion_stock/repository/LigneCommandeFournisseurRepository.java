package com.saki.gestion_stock.repository;

import java.util.List;

import com.saki.gestion_stock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité LigneCommandeFournisseur, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    // Recherche toutes les lignes de commande fournisseur associées à une commande fournisseur spécifique
    // Utilise l'ID de la commande fournisseur comme critère de recherche via la relation ManyToOne
    // Retourne la liste complète des articles commandés dans cette commande fournisseur
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM lignecommandefournisseur WHERE idcommandefournisseur = ?
    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);

    // ATTENTION : Il y a une incohérence dans le nom du paramètre
    // Le paramètre s'appelle "idCommande" mais devrait s'appeler "idArticle" pour correspondre à la sémantique
    // Recherche toutes les lignes de commande fournisseur concernant un article spécifique
    // Utilise l'ID de l'article comme critère de recherche via la relation ManyToOne
    // Retourne l'historique complet des commandes fournisseurs pour cet article
    // Utile pour analyser les achats d'un article ou son historique d'approvisionnement
    List<LigneCommandeFournisseur> findAllByArticleId(Integer idCommande);
}
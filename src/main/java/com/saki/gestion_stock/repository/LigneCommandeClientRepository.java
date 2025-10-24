package com.saki.gestion_stock.repository;

import java.util.List;

import com.saki.gestion_stock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité LigneCommandeClient, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {

    // Recherche toutes les lignes de commande client associées à une commande client spécifique
    // Utilise l'ID de la commande client comme critère de recherche via la relation ManyToOne
    // Retourne la liste complète des articles commandés dans cette commande
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM lignecommandeclient WHERE idcommandeclient = ?
    List<LigneCommandeClient> findAllByCommandeClientId(Integer id);

    // Recherche toutes les lignes de commande client concernant un article spécifique
    // Utilise l'ID de l'article comme critère de recherche via la relation ManyToOne
    // Retourne l'historique complet des commandes clients pour cet article
    // Utile pour analyser les ventes d'un article ou son historique de commandes
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM lignecommandeclient WHERE idarticle = ?
    List<LigneCommandeClient> findAllByArticleId(Integer id);
}
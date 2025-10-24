package com.saki.gestion_stock.repository;

import java.util.List;

import com.saki.gestion_stock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité LigneVente, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {

    // Recherche toutes les lignes de vente concernant un article spécifique
    // Utilise l'ID de l'article comme critère de recherche via la relation ManyToOne
    // Retourne l'historique complet des ventes pour cet article
    // Utile pour analyser les performances de vente d'un article, son historique de ventes,
    // ou pour calculer le chiffre d'affaires généré par un article
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM lignevente WHERE idarticle = ?
    List<LigneVente> findAllByArticleId(Integer idArticle);

    // Recherche toutes les lignes de vente associées à une vente spécifique
    // Utilise l'ID de la vente comme critère de recherche via la relation ManyToOne
    // Retourne le détail complet de tous les articles vendus dans cette vente
    // Utile pour générer un bon de livraison, une facture, ou consulter le détail d'une vente
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM lignevente WHERE idvente = ?
    List<LigneVente> findAllByVenteId(Integer id);
}
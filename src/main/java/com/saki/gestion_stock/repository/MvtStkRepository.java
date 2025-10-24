package com.saki.gestion_stock.repository;

import com.saki.gestion_stock.model.MvtStk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

// Interface de repository pour l'entité MvtStk, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface MvtStkRepository extends JpaRepository<MvtStk, Integer> {

    // Requête personnalisée pour calculer le stock réel d'un article
    // @Query permet de définir une requête JPQL personnalisée
    // COALESCE retourne 0 si la somme est null (aucun mouvement de stock)
    // SUM(m.quantite) fait la somme algébrique des quantités (entrées positives, sorties négatives)
    // @Param permet de lier le paramètre de la méthode au paramètre de la requête JPQL
    // Retourne le stock réel sous forme de BigDecimal pour la précision des calculs
    @Query("SELECT COALESCE(SUM(m.quantite), 0) FROM MvtStk m WHERE m.article.id = :idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);

    // Recherche tous les mouvements de stock pour un article spécifique
    // Utilise l'ID de l'article comme critère de recherche via la relation ManyToOne
    // Retourne l'historique complet des mouvements de stock pour cet article
    // Utile pour tracer l'historique des entrées/sorties, faire un audit ou analyser les flux
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    // La requête équivalente serait : SELECT * FROM mvtstk WHERE idarticle = ? ORDER BY datemvt
    List<MvtStk> findAllByArticleId(Integer idArticle);
}
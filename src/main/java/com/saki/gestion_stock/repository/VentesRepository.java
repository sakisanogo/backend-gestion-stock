package com.saki.gestion_stock.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saki.gestion_stock.model.Ventes;
import java.math.BigDecimal;

// Annotation Spring indiquant que cette interface est un repository
// Gère la persistance et la récupération des données pour l'entité Ventes
@Repository
public interface VentesRepository extends JpaRepository<Ventes, Integer> {

    // Recherche une vente par son code (généralement unique - numéro de facture)
    // Retourne un Optional pour gérer le cas où aucune vente n'est trouvée
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    Optional<Ventes> findVentesByCode(String code);

    // Recherche toutes les ventes comprises entre deux dates
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête avec BETWEEN
    // Utile pour les rapports périodiques (ventes du jour, de la semaine, du mois)
    List<Ventes> findByDateVenteBetween(Instant startDate, Instant endDate);

    // Requête personnalisée pour récupérer les ventes du mois en cours
    // Utilise les fonctions YEAR et MONTH de JPQL avec CURRENT_DATE pour la date actuelle
    // @Query permet une requête JPQL optimisée pour ce cas spécifique
    @Query("SELECT v FROM Ventes v WHERE YEAR(v.dateVente) = YEAR(CURRENT_DATE) AND MONTH(v.dateVente) = MONTH(CURRENT_DATE)")
    List<Ventes> findVentesDuMoisCourant();

    // Requête personnalisée pour récupérer les ventes d'une année spécifique
    // @Param permet de lier le paramètre "annee" à la requête JPQL
    // Utile pour les bilans annuels et les analyses de performance
    @Query("SELECT v FROM Ventes v WHERE YEAR(v.dateVente) = :annee")
    List<Ventes> findVentesByAnnee(@Param("annee") Integer annee);

    // Calcul du chiffre d'affaires total entre deux dates
    // COALESCE retourne 0 si aucune vente n'est trouvée (évite les null)
    // Fait la somme des (quantité * prix unitaire) pour toutes les lignes de vente
    // JOIN v.ligneVentes permet d'accéder aux détails des ventes
    // Retourne un BigDecimal pour la précision des calculs financiers
    @Query("SELECT COALESCE(SUM(l.quantite * l.prixUnitaire), 0) " +
            "FROM Ventes v JOIN v.ligneVentes l " +
            "WHERE v.dateVente BETWEEN :startDate AND :endDate")
    BigDecimal getChiffreAffairesBetweenDates(@Param("startDate") Instant startDate,
                                              @Param("endDate") Instant endDate);

    // Analyse des top articles vendus entre deux dates
    // Retourne une liste de tableaux d'Objects avec [codeArticle, designation, totalVendu]
    // GROUP BY regroupe par article et SUM calcule la quantité totale vendue
    // ORDER BY totalVendu DESC trie du plus vendu au moins vendu
    // Utile pour l'analyse des performances produits et la gestion des stocks
    @Query("SELECT l.article.codeArticle, l.article.designation, SUM(l.quantite) as totalVendu " +
            "FROM Ventes v JOIN v.ligneVentes l " +
            "WHERE v.dateVente BETWEEN :startDate AND :endDate " +
            "GROUP BY l.article.id, l.article.codeArticle, l.article.designation " +
            "ORDER BY totalVendu DESC")
    List<Object[]> getTopArticlesVendus(@Param("startDate") Instant startDate,
                                        @Param("endDate") Instant endDate);

    // Recherche une vente avec ses lignes de vente chargées en eager (évite le problème N+1)
    // LEFT JOIN FETCH charge immédiatement les lignes de vente associées
    // Optimise les performances en évitant les requêtes supplémentaires lazy
    @Query("SELECT v FROM Ventes v LEFT JOIN FETCH v.ligneVentes WHERE v.id = :id")
    Optional<Ventes> findByIdWithLignes(@Param("id") Integer id);

    // Vérifie si un code de vente existe déjà dans la base de données
    // Retourne true si le code existe, false sinon
    // Utile pour éviter les doublons lors de la création de nouvelles ventes
    boolean existsByCode(String code);
}
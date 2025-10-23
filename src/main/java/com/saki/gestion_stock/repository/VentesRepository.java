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

@Repository
public interface VentesRepository extends JpaRepository<Ventes, Integer> {

    Optional<Ventes> findVentesByCode(String code);

    // Ventes entre deux dates
    List<Ventes> findByDateVenteBetween(Instant startDate, Instant endDate);

    // Ventes du mois en cours
    @Query("SELECT v FROM Ventes v WHERE YEAR(v.dateVente) = YEAR(CURRENT_DATE) AND MONTH(v.dateVente) = MONTH(CURRENT_DATE)")
    List<Ventes> findVentesDuMoisCourant();

    // Ventes par année
    @Query("SELECT v FROM Ventes v WHERE YEAR(v.dateVente) = :annee")
    List<Ventes> findVentesByAnnee(@Param("annee") Integer annee);

    // Chiffre d'affaires total
    @Query("SELECT COALESCE(SUM(l.quantite * l.prixUnitaire), 0) " +
            "FROM Ventes v JOIN v.ligneVentes l " +
            "WHERE v.dateVente BETWEEN :startDate AND :endDate")
    BigDecimal getChiffreAffairesBetweenDates(@Param("startDate") Instant startDate,
                                              @Param("endDate") Instant endDate);

    // Top articles vendus
    @Query("SELECT l.article.codeArticle, l.article.designation, SUM(l.quantite) as totalVendu " +
            "FROM Ventes v JOIN v.ligneVentes l " +
            "WHERE v.dateVente BETWEEN :startDate AND :endDate " +
            "GROUP BY l.article.id, l.article.codeArticle, l.article.designation " +
            "ORDER BY totalVendu DESC")
    List<Object[]> getTopArticlesVendus(@Param("startDate") Instant startDate,
                                        @Param("endDate") Instant endDate);

    // Ventes avec leurs lignes (pour éviter le problème N+1)
    @Query("SELECT v FROM Ventes v LEFT JOIN FETCH v.ligneVentes WHERE v.id = :id")
    Optional<Ventes> findByIdWithLignes(@Param("id") Integer id);

    // Vérifier si un code de vente existe déjà
    boolean existsByCode(String code);
}
package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.MvtStkDto;
import java.math.BigDecimal;
import java.util.List;

// Interface de service pour la gestion des mouvements de stock
// Définit le contrat métier pour le suivi et la gestion des flux de stock
public interface MvtStkService {

    // Calcul du stock réel d'un article (somme algébrique de tous les mouvements)
    // Retourne la quantité disponible en stock pour un article donné
    BigDecimal stockReelArticle(Integer idArticle);

    // Consultation de l'historique complet des mouvements de stock pour un article
    // Retourne tous les mouvements (entrées, sorties, corrections) dans l'ordre chronologique
    List<MvtStkDto> mvtStkArticle(Integer idArticle);

    // Enregistrement d'une entrée de stock (quantité positive)
    // Utilisé pour les réceptions de commandes fournisseurs, retours clients, etc.
    MvtStkDto entreeStock(MvtStkDto dto);

    // Enregistrement d'une sortie de stock (quantité négative)
    // Inclut la vérification du stock disponible avant sortie
    // Utilisé pour les ventes, commandes clients, etc.
    MvtStkDto sortieStock(MvtStkDto dto);

    // Correction positive de stock (ajustement manuel positif)
    // Utilisé lors des inventaires quand le stock physique est supérieur au stock théorique
    MvtStkDto correctionStockPos(MvtStkDto dto);

    // Correction négative de stock (ajustement manuel négatif)
    // Utilisé lors des inventaires pour les pertes, vols, ou obsolescence
    MvtStkDto correctionStockNeg(MvtStkDto dto);

}
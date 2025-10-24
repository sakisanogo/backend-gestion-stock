package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneVenteDto;

import java.util.List;

// Interface de service pour la gestion des articles
// Définit le contrat métier pour toutes les opérations liées aux articles
public interface ArticleService {

    // Sauvegarde d'un nouvel article ou mise à jour d'un article existant
    ArticleDto save(ArticleDto dto);

    // Recherche d'un article par son identifiant unique
    ArticleDto findById(Integer id);

    // Recherche d'un article par son code article (généralement unique)
    ArticleDto findByCodeArticle(String codeArticle);

    // Récupération de tous les articles du système
    List<ArticleDto> findAll();

    // Consultation de l'historique des ventes pour un article spécifique
    // Retourne toutes les lignes de vente où l'article apparaît
    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    // Consultation de l'historique des commandes clients pour un article spécifique
    // NOTE: Il y a une faute de frappe dans le nom de méthode "findHistoriaueCommandeClient"
    List<LigneCommandeClientDto> findHistoriaueCommandeClient(Integer idArticle);

    // Consultation de l'historique des commandes fournisseurs pour un article spécifique
    // Utile pour tracer les approvisionnements d'un article
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

    // Recherche de tous les articles appartenant à une catégorie spécifique
    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);

    // Suppression d'un article avec vérification des dépendances
    void delete(Integer id);

}
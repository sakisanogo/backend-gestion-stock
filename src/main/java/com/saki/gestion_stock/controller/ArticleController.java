// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.ArticleApi;
import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneVenteDto;
// Importation du service Article
import com.saki.gestion_stock.services.ArticleService;
// Importations des annotations Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class ArticleController implements ArticleApi {

    // Déclaration du service Article
    private ArticleService articleService;

    // Injection de dépendance via le constructeur
    @Autowired
    public ArticleController(
            ArticleService articleService
    ) {
        this.articleService = articleService;
    }

    // Implémentation de la méthode de sauvegarde d'article
    @Override
    public ArticleDto save(ArticleDto dto) {
        // Délégation au service pour sauvegarder l'article
        return articleService.save(dto);
    }

    // Implémentation de la méthode de recherche d'article par ID
    @Override
    public ArticleDto findById(Integer id) {
        // Délégation au service pour trouver l'article par son ID
        return articleService.findById(id);
    }

    // Implémentation de la méthode de recherche d'article par code
    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        // Délégation au service pour trouver l'article par son code
        return articleService.findByCodeArticle(codeArticle);
    }

    // Implémentation de la méthode pour récupérer tous les articles
    @Override
    public List<ArticleDto> findAll() {
        // Délégation au service pour récupérer tous les articles
        return articleService.findAll();
    }

    // Implémentation de la méthode pour récupérer l'historique des ventes d'un article
    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        // Délégation au service pour récupérer l'historique des ventes
        return articleService.findHistoriqueVentes(idArticle);
    }

    // Implémentation de la méthode pour récupérer l'historique des commandes clients d'un article
    @Override
    public List<LigneCommandeClientDto> findHistoriaueCommandeClient(Integer idArticle) {
        // Délégation au service pour récupérer l'historique des commandes clients
        return articleService.findHistoriaueCommandeClient(idArticle);
    }

    // Implémentation de la méthode pour récupérer l'historique des commandes fournisseurs d'un article
    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        // Délégation au service pour récupérer l'historique des commandes fournisseurs
        return articleService.findHistoriqueCommandeFournisseur(idArticle);
    }

    // Implémentation de la méthode pour récupérer tous les articles d'une catégorie
    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        // Délégation au service pour récupérer les articles par catégorie
        return articleService.findAllArticleByIdCategory(idCategory);
    }

    // Implémentation de la méthode de suppression d'article
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer l'article
        articleService.delete(id);
    }
}
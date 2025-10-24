package com.saki.gestion_stock.repository;

import java.util.List;
import java.util.Optional;

import com.saki.gestion_stock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité Article, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    // Recherche un article par son code article (unique)
    // Retourne un Optional pour gérer le cas où aucun article n'est trouvé
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL
    Optional<Article> findArticleByCodeArticle(String codeArticle);

    // Recherche tous les articles appartenant à une catégorie spécifique
    // Utilise l'ID de la catégorie comme critère de recherche
    // Retourne une liste d'articles (peut être vide si aucun article dans la catégorie)
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête avec la jointure
    List<Article> findAllByCategoryId(Integer idCategory);
}
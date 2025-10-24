package com.saki.gestion_stock.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.CategoryDto;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.model.Article;
import com.saki.gestion_stock.model.LigneCommandeClient;
import com.saki.gestion_stock.model.LigneCommandeFournisseur;
import com.saki.gestion_stock.model.LigneVente;
import com.saki.gestion_stock.model.MvtStk;
import com.saki.gestion_stock.repository.ArticleRepository;
import com.saki.gestion_stock.repository.CategoryRepository;
import com.saki.gestion_stock.repository.LigneCommandeClientRepository;
import com.saki.gestion_stock.repository.LigneCommandeFournisseurRepository;
import com.saki.gestion_stock.repository.LigneVenteRepository;
import com.saki.gestion_stock.repository.MvtStkRepository;
import com.saki.gestion_stock.services.CategoryService;
import com.saki.gestion_stock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// Service d'implémentation pour la gestion des catégories d'articles
// @Slf4j génère automatiquement un logger SLF4J
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    // Injection des repositories nécessaires pour gérer les catégories et leurs dépendances
    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkRepository mvtStkRepository;

    // Constructeur avec injection de toutes les dépendances nécessaires
    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ArticleRepository articleRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository,
            LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
            LigneVenteRepository ligneVenteRepository,
            MvtStkRepository mvtStkRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkRepository = mvtStkRepository;
    }

    // Sauvegarde d'une nouvelle catégorie avec validation
    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Category is not valid {}", dto);
            throw new InvalidEntityException("La category n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        // Conversion DTO -> Entity, sauvegarde, puis conversion Entity -> DTO
        return CategoryDto.fromEntity(
                categoryRepository.save(CategoryDto.toEntity(dto))
        );
    }

    // Recherche d'une catégorie par son ID
    @Override
    public CategoryDto findById(Integer id) {
        if (id == null) {
            log.error("Category ID is null");
            return null;
        }
        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune category avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    // Recherche d'une catégorie par son code
    @Override
    public CategoryDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Category CODE is null");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune category avec le CODE = " + code + " n' ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
                );
    }

    // Récupération de toutes les catégories
    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Suppression en cascade d'une catégorie et de tous ses articles avec leurs dépendances
    // @Transactional assure que toutes les opérations sont atomiques (tout ou rien)
    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            log.error("Category ID is null");
            return;
        }

        // Vérifier si la catégorie existe (lève une exception si non trouvée)
        findById(id);

        log.info("Tentative de suppression de la catégorie ID: {}", id);

        // Récupérer tous les articles de cette catégorie
        List<Article> articles = articleRepository.findAllByCategoryId(id);

        if (!articles.isEmpty()) {
            log.info("Suppression de {} articles pour la catégorie ID: {}", articles.size(), id);

            // Pour chaque article, supprimer toutes les dépendances en cascade
            for (Article article : articles) {
                Integer articleId = article.getId();

                // 1. Supprimer les mouvements de stock (données techniques)
                List<MvtStk> mouvementsStock = mvtStkRepository.findAllByArticleId(articleId);
                if (!mouvementsStock.isEmpty()) {
                    log.info("Suppression de {} mouvements de stock pour l'article ID: {}", mouvementsStock.size(), articleId);
                    mvtStkRepository.deleteAll(mouvementsStock);
                }

                // 2. Supprimer les lignes de commande client
                List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(articleId);
                if (!ligneCommandeClients.isEmpty()) {
                    log.info("Suppression de {} lignes de commande client pour l'article ID: {}", ligneCommandeClients.size(), articleId);
                    ligneCommandeClientRepository.deleteAll(ligneCommandeClients);
                }

                // 3. Supprimer les lignes de commande fournisseur
                List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(articleId);
                if (!ligneCommandeFournisseurs.isEmpty()) {
                    log.info("Suppression de {} lignes de commande fournisseur pour l'article ID: {}", ligneCommandeFournisseurs.size(), articleId);
                    ligneCommandeFournisseurRepository.deleteAll(ligneCommandeFournisseurs);
                }

                // 4. Supprimer les lignes de vente
                List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(articleId);
                if (!ligneVentes.isEmpty()) {
                    log.info("Suppression de {} lignes de vente pour l'article ID: {}", ligneVentes.size(), articleId);
                    ligneVenteRepository.deleteAll(ligneVentes);
                }

                // 5. Supprimer l'article lui-même après avoir nettoyé toutes ses dépendances
                articleRepository.delete(article);
                log.info("Article ID: {} supprimé", articleId);
            }

            log.info("Tous les articles et leurs dépendances ont été supprimés pour la catégorie ID: {}", id);
        }

        // Maintenant supprimer la catégorie elle-même (tous ses articles ont été supprimés)
        categoryRepository.deleteById(id);
        log.info("Catégorie ID: {} supprimée avec succès", id);
    }
}
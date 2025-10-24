package com.saki.gestion_stock.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneVenteDto;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.model.LigneCommandeClient;
import com.saki.gestion_stock.model.LigneCommandeFournisseur;
import com.saki.gestion_stock.model.LigneVente;
import com.saki.gestion_stock.model.MvtStk;
import com.saki.gestion_stock.repository.ArticleRepository;
import com.saki.gestion_stock.repository.LigneCommandeClientRepository;
import com.saki.gestion_stock.repository.LigneCommandeFournisseurRepository;
import com.saki.gestion_stock.repository.LigneVenteRepository;
import com.saki.gestion_stock.repository.MvtStkRepository;
import com.saki.gestion_stock.services.ArticleService;
import com.saki.gestion_stock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// Service d'implémentation pour la gestion des articles
// @Slf4j génère automatiquement un logger SLF4J
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    // Injection des repositories nécessaires pour les opérations sur les articles et leurs dépendances
    private ArticleRepository articleRepository;
    private LigneVenteRepository venteRepository;
    private LigneCommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeClientRepository commandeClientRepository;
    private MvtStkRepository mvtStkRepository; // Repository pour les mouvements de stock

    // Constructeur avec injection de dépendances
    @Autowired
    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            LigneVenteRepository venteRepository,
            LigneCommandeFournisseurRepository commandeFournisseurRepository,
            LigneCommandeClientRepository commandeClientRepository,
            MvtStkRepository mvtStkRepository) { // Ajout dans le constructeur
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.commandeClientRepository = commandeClientRepository;
        this.mvtStkRepository = mvtStkRepository;
    }

    // Sauvegarde d'un nouvel article avec validation
    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        // Conversion DTO -> Entity, sauvegarde, puis conversion Entity -> DTO
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto)
                )
        );
    }

    // Recherche d'un article par son ID
    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return null;
        }

        return articleRepository.findById(id).map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    // Recherche d'un article par son code article
    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (!StringUtils.hasLength(codeArticle)) {
            log.error("Article CODE is null");
            return null;
        }

        return articleRepository.findArticleByCodeArticle(codeArticle)
                .map(ArticleDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun article avec le CODE = " + codeArticle + " n' ete trouve dans la BDD",
                                ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    // Récupération de tous les articles
    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Historique des ventes pour un article spécifique
    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return venteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Historique des commandes clients pour un article spécifique
    // NOTE: Il y a une faute de frappe dans le nom de méthode "findHistoriaueCommandeClient"
    @Override
    public List<LigneCommandeClientDto> findHistoriaueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Historique des commandes fournisseurs pour un article spécifique
    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Recherche de tous les articles d'une catégorie spécifique
    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Suppression d'un article avec vérification des dépendances
    // @Transactional assure que toutes les opérations sont atomiques
    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return;
        }

        // Vérifier si l'article existe (lève une exception si non trouvé)
        findById(id);

        log.info("Tentative de suppression de l'article ID: {}", id);

        // Vérifier et supprimer les mouvements de stock en premier
        // Les mouvements de stock sont supprimés silencieusement car ce sont des données techniques
        List<MvtStk> mouvementsStock = mvtStkRepository.findAllByArticleId(id);
        if (!mouvementsStock.isEmpty()) {
            log.info("Suppression de {} mouvements de stock pour l'article ID: {}", mouvementsStock.size(), id);
            mvtStkRepository.deleteAll(mouvementsStock);
            log.info("Mouvements de stock supprimés avec succès");
        }

        // Vérifier les autres dépendances métier qui empêchent la suppression

        // Vérification des commandes clients
        List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        // Vérification des commandes fournisseurs
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        // Vérification des ventes
        List<LigneVente> ligneVentes = venteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        // Si aucune dépendance métier n'existe, suppression de l'article
        articleRepository.deleteById(id);
        log.info("Article ID: {} supprimé avec succès", id);
    }
}
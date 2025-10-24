package com.saki.gestion_stock.services.Impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.*;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.model.*;
import com.saki.gestion_stock.repository.ArticleRepository;
import com.saki.gestion_stock.repository.CommandeFournisseurRepository;
import com.saki.gestion_stock.repository.FournisseurRepository;
import com.saki.gestion_stock.repository.LigneCommandeFournisseurRepository;
import com.saki.gestion_stock.services.CommandeFournisseurService;
import com.saki.gestion_stock.services.MvtStkService;
import com.saki.gestion_stock.validator.ArticleValidator;
import com.saki.gestion_stock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// Service d'implémentation pour la gestion des commandes fournisseurs
// @Slf4j génère automatiquement un logger SLF4J
@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    // Injection des repositories et services nécessaires
    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService; // Service pour gérer les mouvements de stock

    // Constructeur avec injection de dépendances
    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          FournisseurRepository fournisseurRepository, ArticleRepository articleRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;
    }

    // Sauvegarde d'une nouvelle commande fournisseur avec validation complète
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        log.info("Début de sauvegarde de commande fournisseur");

        List<String> errors = CommandeFournisseurValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Commande fournisseur n'est pas valide: {}", errors);
            throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        // Vérification qu'on ne modifie pas une commande déjà livrée
        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        // Validation du fournisseur
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if (fournisseur.isEmpty()) {
            log.warn("Fournisseur with ID {} was not found in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec l'ID " + dto.getFournisseur().getId() + " n'a ete trouve dans la BDD",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        // Validation des articles de la commande
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticle().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdFrs.getArticle().getId() + " n'existe pas");
                    }
                } else {
                    articleErrors.add("Impossible d'enregister une commande avec un article NULL");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("Erreurs articles: {}", articleErrors);
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        // Conversion DTO -> Entity et configuration des données
        CommandeFournisseur commandeEntity = CommandeFournisseurDto.toEntity(dto);

        if (commandeEntity.getDateCommande() == null) {
            commandeEntity.setDateCommande(Instant.now());
        }

        commandeEntity.setFournisseur(fournisseur.get());

        // Sauvegarde de la commande fournisseur
        CommandeFournisseur savedCmdFrs = commandeFournisseurRepository.save(commandeEntity);
        log.info("Commande fournisseur sauvegardée avec succès, ID: {}", savedCmdFrs.getId());

        // Sauvegarde des lignes de commande et gestion des entrées de stock
        if (dto.getLigneCommandeFournisseurs() != null) {
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrs);
                ligneCommandeFournisseur.setCommandeFournisseur(savedCmdFrs);
                ligneCommandeFournisseur.setIdEntreprise(savedCmdFrs.getIdEntreprise());

                LigneCommandeFournisseur saveLigne = ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
                effectuerEntree(saveLigne); // Effectue l'entrée de stock
            });
        }

        return CommandeFournisseurDto.fromEntityWithLignes(savedCmdFrs);
    }

    // Recherche d'une commande fournisseur par son ID
    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidEntityException("L'ID de la commande fournisseur ne peut pas être null", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID);
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntityWithLignes)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    // Recherche d'une commande fournisseur par son code
    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande fournisseur CODE is NULL");
            throw new InvalidEntityException("Le code de la commande fournisseur ne peut pas être vide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID);
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntityWithLignes)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    // Récupération de toutes les commandes fournisseurs (sans les lignes pour éviter la récursion)
    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(commande -> createBasicCommandeDto(commande))
                .collect(Collectors.toList());
    }

    // Récupération de toutes les lignes d'une commande fournisseur
    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        if (idCommande == null) {
            log.error("ID commande fournisseur is NULL");
            throw new InvalidEntityException("L'ID de la commande fournisseur ne peut pas être null", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID);
        }
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Suppression d'une commande fournisseur avec ses lignes
    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidEntityException("L'ID de la commande fournisseur ne peut pas être null", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID);
        }

        // Vérifier si la commande existe
        CommandeFournisseur commande = commandeFournisseurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande fournisseur trouvée avec l'ID " + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));

        log.info("🔴 Début suppression commande fournisseur ID: {}", id);

        // 1. D'abord supprimer toutes les lignes de commande
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            log.info("📦 Suppression de {} lignes de commande", ligneCommandeFournisseurs.size());
            ligneCommandeFournisseurRepository.deleteAll(ligneCommandeFournisseurs);
            log.info("✅ Lignes de commande supprimées");
        }

        // 2. Puis supprimer la commande
        commandeFournisseurRepository.delete(commande);
        log.info("✅ Commande fournisseur supprimée avec succès, ID: {}", id);
    }

    // Mise à jour de l'état d'une commande fournisseur
    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (etatCommande == null) {
            log.error("L'etat de la commande fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);

        CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        if (commandeFournisseur.isCommandeLivree()) {
            updateMvtStk(idCommande); // Met à jour les mouvements de stock si la commande est livrée
        }
        return CommandeFournisseurDto.fromEntityWithLignes(savedCommande);
    }

    // Mise à jour de la quantité d'une ligne de commande
    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Quantité invalide: {}", quantite);
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou inférieure/égale à ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFounisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFounisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFounisseur);

        log.info("Quantité mise à jour: Commande ID {}, Ligne ID {}, Nouvelle quantité: {}",
                idCommande, idLigneCommande, quantite);
        return commandeFournisseur;
    }

    // Mise à jour du fournisseur d'une commande
    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
        if (fournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }
        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        CommandeFournisseur savedCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
        log.info("Fournisseur mis à jour: Commande ID {}, Nouveau fournisseur ID: {}", idCommande, idFournisseur);
        return CommandeFournisseurDto.fromEntityWithLignes(savedCmdFrs);
    }

    // Mise à jour de l'article d'une ligne de commande
    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        log.info("Article mis à jour: Commande ID {}, Ligne ID {}, Nouvel article ID: {}",
                idCommande, idLigneCommande, idArticle);
        return commandeFournisseur;
    }

    // Suppression d'un article d'une commande
    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        log.info("Article supprimé: Commande ID {}, Ligne ID {}", idCommande, idLigneCommande);
        return commandeFournisseur;
    }

    // ============ MÉTHODES PRIVÉES ============

    // Crée un DTO basique sans les lignes pour éviter les problèmes de récursion
    private CommandeFournisseurDto createBasicCommandeDto(CommandeFournisseur commande) {
        if (commande == null) {
            return null;
        }

        CommandeFournisseurDto dto = new CommandeFournisseurDto();
        dto.setId(commande.getId());
        dto.setCode(commande.getCode());
        dto.setDateCommande(commande.getDateCommande());
        dto.setEtatCommande(commande.getEtatCommande());
        dto.setIdEntreprise(commande.getIdEntreprise());

        // Fournisseur basique sans relations circulaires
        if (commande.getFournisseur() != null) {
            FournisseurDto fournisseurDto = FournisseurDto.builder()
                    .id(commande.getFournisseur().getId())
                    .nom(commande.getFournisseur().getNom())
                    .prenom(commande.getFournisseur().getPrenom())
                    .mail(commande.getFournisseur().getMail())
                    .numTel(commande.getFournisseur().getNumTel())
                    .photo(commande.getFournisseur().getPhoto())
                    .idEntreprise(commande.getFournisseur().getIdEntreprise())
                    .build();
            dto.setFournisseur(fournisseurDto);
        }

        // NE PAS inclure les lignes de commande pour éviter la récursion
        dto.setLigneCommandeFournisseurs(null);

        return dto;
    }

    // Vérifie qu'une commande n'est pas livrée (sinon non modifiable)
    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);
        if (commandeFournisseur.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeFournisseur;
    }

    // Recherche une ligne de commande par son ID
    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    // Méthodes de validation des IDs
    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    // Met à jour les mouvements de stock pour une commande livrée
    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseur.forEach(lig -> {
            effectuerEntree(lig);
        });
        log.info("Mouvements de stock mis à jour pour la commande ID: {}", idCommande);
    }

    // Effectue une entrée de stock pour une ligne de commande
    private void effectuerEntree(LigneCommandeFournisseur lig) {
        if (lig.getArticle() == null) {
            log.error("Impossible d'effectuer l'entrée: l'article est null pour la ligne de commande ID: {}", lig.getId());
            throw new InvalidOperationException("Impossible d'effectuer l'entrée de stock: article manquant",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        // Création du mouvement de stock d'entrée
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.entreeStock(mvtStkDto);
        log.debug("Entrée de stock effectuée pour l'article ID: {}, quantité: {}",
                lig.getArticle().getId(), lig.getQuantite());
    }
}
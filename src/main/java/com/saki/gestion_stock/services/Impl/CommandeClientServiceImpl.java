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
import com.saki.gestion_stock.repository.ClientRepository;
import com.saki.gestion_stock.repository.CommandeClientRepository;
import com.saki.gestion_stock.repository.LigneCommandeClientRepository;
import com.saki.gestion_stock.services.CommandeClientService;
import com.saki.gestion_stock.services.MvtStkService;
import com.saki.gestion_stock.validator.ArticleValidator;
import com.saki.gestion_stock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,
                                     ClientRepository clientRepository, ArticleRepository articleRepository,
                                     LigneCommandeClientRepository ligneCommandeClientRepository,
                                     MvtStkService mvtStkService) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        log.info("Début de sauvegarde de commande client");

        List<String> errors = CommandeClientValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Commande client n'est pas valide: {}", errors);
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }

        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        // Validation du client
        if (dto.getClient() == null || dto.getClient().getId() == null) {
            log.error("Client ou ID client est NULL");
            throw new InvalidEntityException("Le client doit avoir un ID valide", ErrorCodes.CLIENT_NOT_FOUND);
        }

        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if (client.isEmpty()) {
            log.warn("Client with ID {} was not found in the DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID " + dto.getClient().getId() + " n'a ete trouve dans la BDD",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        // Validation des articles - CORRECTION PRINCIPALE ICI
        if (dto.getLigneCommandeClients() != null) {
            dto.getLigneCommandeClients().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticle() != null) {
                    // VÉRIFICATION CRITIQUE AJOUTÉE : s'assurer que l'ID n'est pas null
                    if (ligCmdClt.getArticle().getId() == null) {
                        articleErrors.add("L'ID de l'article ne peut pas être NULL");
                    } else {
                        Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                        if (article.isEmpty()) {
                            articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticle().getId() + " n'existe pas");
                        } else {
                            // Validation supplémentaire de l'article
                            List<String> articleValidationErrors = ArticleValidator.validate(ArticleDto.fromEntity(article.get()));
                            if (!articleValidationErrors.isEmpty()) {
                                articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticle().getId() + " n'est pas valide: " + articleValidationErrors);
                            }
                        }
                    }
                } else {
                    articleErrors.add("Impossible d'enregister une commande avec un article NULL");
                }
            });
        } else {
            articleErrors.add("La commande doit contenir au moins une ligne de commande");
        }

        if (!articleErrors.isEmpty()) {
            log.warn("Erreurs de validation des articles: {}", articleErrors);
            throw new InvalidEntityException("Erreur de validation des articles", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        dto.setDateCommande(Instant.now());
        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        // Sauvegarde des lignes de commande
        if (dto.getLigneCommandeClients() != null) {
            dto.getLigneCommandeClients().forEach(ligCmdClt -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligCmdClt);
                ligneCommandeClient.setCommandeClient(savedCmdClt);
                ligneCommandeClient.setIdEntreprise(dto.getIdEntreprise());

                // S'assurer que l'article est correctement défini
                if (ligCmdClt.getArticle() != null && ligCmdClt.getArticle().getId() != null) {
                    Article article = articleRepository.findById(ligCmdClt.getArticle().getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Article non trouvé avec l'ID " + ligCmdClt.getArticle().getId(),
                                    ErrorCodes.ARTICLE_NOT_FOUND));
                    ligneCommandeClient.setArticle(article);
                }

                LigneCommandeClient savedLigneCmd = ligneCommandeClientRepository.save(ligneCommandeClient);
                effectuerSortie(savedLigneCmd);
            });
        }

        log.info("Commande client sauvegardée avec succès, ID: {}", savedCmdClt.getId());
        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
            throw new InvalidEntityException("L'ID de la commande client ne peut pas être null", ErrorCodes.COMMANDE_CLIENT_NOT_VALID);
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande client CODE is NULL");
            throw new InvalidEntityException("Le code de la commande client ne peut pas être vide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID);
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
            throw new InvalidEntityException("L'ID de la commande client ne peut pas être null", ErrorCodes.COMMANDE_CLIENT_NOT_VALID);
        }

        // Vérifier si la commande existe
        CommandeClient commande = commandeClientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande client trouvée avec l'ID " + id, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));

        log.info("🔴 Début suppression commande client ID: {}", id);

        // 1. D'abord supprimer toutes les lignes de commande
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
        if (!ligneCommandeClients.isEmpty()) {
            log.info("📦 Suppression de {} lignes de commande", ligneCommandeClients.size());
            ligneCommandeClientRepository.deleteAll(ligneCommandeClients);
            log.info("✅ Lignes de commande supprimées");
        }

        // 2. Puis supprimer la commande
        commandeClientRepository.delete(commande);
        log.info("✅ Commande client supprimée avec succès, ID: {}", id);
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        if (idCommande == null) {
            log.error("ID commande client is NULL");
            throw new InvalidEntityException("L'ID de la commande client ne peut pas être null", ErrorCodes.COMMANDE_CLIENT_NOT_VALID);
        }
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (etatCommande == null) {
            log.error("L'etat de la commande client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        commandeClient.setEtatCommande(etatCommande);

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
        if (commandeClient.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }

        log.info("État de la commande mis à jour: ID {}, Nouvel état: {}", idCommande, etatCommande);
        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Quantité invalide: {}", quantite);
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou inférieure/égale à ZERO",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        log.info("Quantité mise à jour: Commande ID {}, Ligne ID {}, Nouvelle quantité: {}",
                idCommande, idLigneCommande, quantite);
        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient == null) {
            log.error("L'ID du client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<Client> clientOptional = clientRepository.findById(idClient);
        if (clientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun client n'a ete trouve avec l'ID " + idClient, ErrorCodes.CLIENT_NOT_FOUND);
        }
        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
        log.info("Client mis à jour: Commande ID {}, Nouveau client ID: {}", idCommande, idClient);
        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);

        log.info("Article mis à jour: Commande ID {}, Ligne ID {}, Nouvel article ID: {}",
                idCommande, idLigneCommande, idArticle);
        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeClient and inform the client in case it is absent
        findLigneCommandeClient(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);

        log.info("Article supprimé: Commande ID {}, Ligne ID {}", idCommande, idLigneCommande);
        return commandeClient;
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande) {
        CommandeClientDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClient;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommande);
        if (ligneCommandeClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande client n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig -> {
            effectuerSortie(lig);
        });
        log.info("Mouvements de stock mis à jour pour la commande ID: {}", idCommande);
    }

    private void effectuerSortie(LigneCommandeClient lig) {
        if (lig.getArticle() == null) {
            log.error("Impossible d'effectuer la sortie: l'article est null pour la ligne de commande ID: {}", lig.getId());
            throw new InvalidOperationException("Impossible d'effectuer la sortie de stock: article manquant",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvtStkService.sortieStock(mvtStkDto);
        log.debug("Sortie de stock effectuée pour l'article ID: {}, quantité: {}",
                lig.getArticle().getId(), lig.getQuantite());
    }
}
package com.saki.gestion_stock.services.Impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.dto.LigneVenteDto;
import com.saki.gestion_stock.dto.MvtStkDto;
import com.saki.gestion_stock.dto.VentesDto;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.model.*;
import com.saki.gestion_stock.repository.ArticleRepository;
import com.saki.gestion_stock.repository.LigneVenteRepository;
import com.saki.gestion_stock.repository.VentesRepository;
import com.saki.gestion_stock.services.MvtStkService;
import com.saki.gestion_stock.services.VentesService;
import com.saki.gestion_stock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

// Service d'implémentation pour la gestion des ventes
// Service critique pour le processus commercial et la gestion des stocks
@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkService mvtStkService; // Service pour gérer les mouvements de stock

    @Autowired
    public VentesServiceImpl(ArticleRepository articleRepository, VentesRepository ventesRepository,
                             LigneVenteRepository ligneVenteRepository, MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkService = mvtStkService;
    }

    // Sauvegarde d'une nouvelle vente avec validation complète
    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VentesValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Ventes n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        List<String> articleErrors = new ArrayList<>();

        // Validation de tous les articles de la vente
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (article.isEmpty()) {
                articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId() + " n'a ete trouve dans la BDD");
            }
        });

        if (!articleErrors.isEmpty()) {
            log.error("One or more articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas ete trouve dans la BDD", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        // Sauvegarde de l'entête de la vente
        Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));

        // Sauvegarde des lignes de vente et mise à jour du stock
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVentes); // Lien avec la vente parente
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente); // Mise à jour du stock (sortie)
        });

        return VentesDto.fromEntity(savedVentes);
    }

    // Recherche d'une vente par son ID
    @Override
    public VentesDto findById(Integer id) {
        if (id == null) {
            log.error("Ventes ID is NULL");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun vente n'a ete trouve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
    }

    // Recherche d'une vente par son code (numéro de facture)
    @Override
    public VentesDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Vente CODE is NULL");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente client n'a ete trouve avec le CODE " + code, ErrorCodes.VENTE_NOT_VALID
                ));
    }

    // Récupération de toutes les ventes
    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Suppression d'une vente avec vérification des dépendances
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Vente ID is NULL");
            return;
        }
        // Vérification qu'il n'y a pas de lignes de vente associées
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une vente ...",
                    ErrorCodes.VENTE_ALREADY_IN_USE);
        }
        ventesRepository.deleteById(id);
    }

    // Met à jour le mouvement de stock pour une ligne de vente
    private void updateMvtStk(LigneVente lig) {
        // Création d'un mouvement de stock de sortie
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle())) // Article vendu
                .dateMvt(Instant.now())                           // Date actuelle
                .typeMvt(TypeMvtStk.SORTIE)                       // Type : sortie de stock
                .sourceMvt(SourceMvtStk.VENTE)                    // Source : vente
                .quantite(lig.getQuantite())                      // Quantité vendue
                .idEntreprise(lig.getIdEntreprise())              // ID entreprise pour multi-locataire
                .build();
        mvtStkService.sortieStock(mvtStkDto); // Appel du service de stock

        log.debug("Sortie de stock enregistrée pour l'article ID: {}, quantité: {}",
                lig.getArticle().getId(), lig.getQuantite());
    }
}
package com.saki.gestion_stock.services.Impl;

import com.saki.gestion_stock.dto.MvtStkDto;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.model.TypeMvtStk;
import com.saki.gestion_stock.repository.MvtStkRepository;
import com.saki.gestion_stock.services.ArticleService;
import com.saki.gestion_stock.services.MvtStkService;
import com.saki.gestion_stock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

// Service d'implémentation pour la gestion des mouvements de stock
// Service critique pour l'intégrité des niveaux de stock
@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {

    private MvtStkRepository repository;
    private ArticleService articleService;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository repository, ArticleService articleService) {
        this.repository = repository;
        this.articleService = articleService;
    }

    // Calcul du stock réel d'un article (somme algébrique de tous les mouvements)
    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if (idArticle == null) {
            log.warn("ID article is NULL");
            return BigDecimal.valueOf(-1); // Retourne -1 pour indiquer une erreur
        }
        // Vérifie que l'article existe avant de calculer le stock
        articleService.findById(idArticle);
        // Appel au repository qui exécute la requête SUM(quantite) WHERE idArticle = ?
        return repository.stockReelArticle(idArticle);
    }

    // Récupère l'historique complet des mouvements de stock pour un article
    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        return repository.findAllByArticleId(idArticle).stream()
                .map(MvtStkDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Entrée de stock (quantité positive) - ex: réception commande fournisseur
    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return entreePositive(dto, TypeMvtStk.ENTREE);
    }

    // Sortie de stock (quantité négative) - ex: vente, commande client
    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvtStk.SORTIE);
    }

    // Correction positive de stock - ex: inventaire avec écart positif
    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return entreePositive(dto, TypeMvtStk.CORRECTION_POS);
    }

    // Correction négative de stock - ex: inventaire avec écart négatif, perte
    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvtStk.CORRECTION_NEG);
    }

    // Méthode générique pour les mouvements positifs (entrées et corrections positives)
    private MvtStkDto entreePositive(MvtStkDto dto, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }
        // S'assure que la quantité est positive (valeur absolue)
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue())
                )
        );
        dto.setTypeMvt(typeMvtStk);
        dto.setDateMvt(Instant.now()); // Horodatage automatique
        return MvtStkDto.fromEntity(
                repository.save(MvtStkDto.toEntity(dto))
        );
    }

    // Méthode générique pour les mouvements négatifs (sorties et corrections négatives)
    private MvtStkDto sortieNegative(MvtStkDto dto, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Le mouvement du stock n'est pas valide", ErrorCodes.MVT_STK_NOT_VALID, errors);
        }

        // VÉRIFICATION CRITIQUE : contrôle du stock disponible avant sortie
        BigDecimal stockActuel = stockReelArticle(dto.getArticle().getId());
        BigDecimal quantiteDemandee = dto.getQuantite().abs(); // Quantité en valeur absolue

        // Vérifie que le stock est suffisant pour la sortie demandée
        if (stockActuel == null || stockActuel.compareTo(quantiteDemandee) < 0) {
            throw new InvalidOperationException(
                    "Stock insuffisant. Stock actuel: " + stockActuel + ", Quantité demandée: " + quantiteDemandee,
                    ErrorCodes.STOCK_INSUFFISANT
            );
        }

        // Convertit la quantité en négatif pour les sorties
        dto.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(dto.getQuantite().doubleValue()) * -1
                )
        );
        dto.setTypeMvt(typeMvtStk);
        dto.setDateMvt(Instant.now()); // Horodatage automatique
        return MvtStkDto.fromEntity(
                repository.save(MvtStkDto.toEntity(dto))
        );
    }
}
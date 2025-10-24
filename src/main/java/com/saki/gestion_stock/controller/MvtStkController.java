// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importations des classes nécessaires
import java.math.BigDecimal;
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.MvtStkApi;
import com.saki.gestion_stock.dto.MvtStkDto;
// Importation du service MvtStk
import com.saki.gestion_stock.services.MvtStkService;
// Importations des classes Spring
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class MvtStkController implements MvtStkApi {

    // Déclaration du service MvtStk (nom de variable simplifié "service")
    private MvtStkService service;

    // Injection de dépendance via le constructeur
    @Autowired
    public MvtStkController(MvtStkService service) {
        this.service = service;
    }

    // Implémentation de la méthode pour récupérer le stock réel d'un article
    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        // Délégation au service pour calculer le stock réel de l'article
        return service.stockReelArticle(idArticle);
    }

    // Implémentation de la méthode pour récupérer l'historique des mouvements de stock d'un article
    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        // Délégation au service pour récupérer tous les mouvements de stock d'un article
        return service.mvtStkArticle(idArticle);
    }

    // Implémentation de la méthode pour enregistrer une entrée de stock
    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        // Délégation au service pour enregistrer un mouvement d'entrée de stock
        return service.entreeStock(dto);
    }

    // Implémentation de la méthode pour enregistrer une sortie de stock
    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        // Délégation au service pour enregistrer un mouvement de sortie de stock
        return service.sortieStock(dto);
    }

    // Implémentation de la méthode pour enregistrer une correction positive de stock
    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        // Délégation au service pour enregistrer une correction positive (ajustement positif)
        return service.correctionStockPos(dto);
    }

    // Implémentation de la méthode pour enregistrer une correction négative de stock
    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        // Délégation au service pour enregistrer une correction négative (ajustement négatif)
        return service.correctionStockNeg(dto);
    }
}
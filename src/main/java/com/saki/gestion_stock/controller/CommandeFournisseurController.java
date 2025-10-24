// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importations des classes nécessaires
import java.math.BigDecimal;
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.CommandeFournisseurApi;
import com.saki.gestion_stock.dto.CommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.model.EtatCommande;
// Importation du service CommandeFournisseur
import com.saki.gestion_stock.services.CommandeFournisseurService;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    // Déclaration du service CommandeFournisseur
    private CommandeFournisseurService commandeFournisseurService;

    // Injection de dépendance via le constructeur
    @Autowired
    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    // Implémentation de la méthode de sauvegarde de commande fournisseur avec logging détaillé
    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        try {
            // Logs de débogage pour tracer la création de commande fournisseur
            System.out.println("=== CONTROLLER - CREATE COMMANDE FOURNISSEUR ===");
            System.out.println("Code: " + dto.getCode());
            System.out.println("Fournisseur ID: " + (dto.getFournisseur() != null ? dto.getFournisseur().getId() : "null"));
            System.out.println("Date: " + dto.getDateCommande());
            System.out.println("Lignes: " + (dto.getLigneCommandeFournisseurs() != null ? dto.getLigneCommandeFournisseurs().size() : 0));

            // Délégation au service pour sauvegarder la commande fournisseur
            CommandeFournisseurDto saved = commandeFournisseurService.save(dto);

            // Log de confirmation de création réussie
            System.out.println("✅ CONTROLLER - Commande créée avec ID: " + saved.getId());
            return saved;

        } catch (Exception e) {
            // Log d'erreur détaillé en cas d'exception
            System.err.println("❌ CONTROLLER ERREUR: " + e.getClass().getSimpleName());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw e;  // Propagation de l'exception
        }
    }

    // Implémentation de la méthode de mise à jour de l'état de commande fournisseur
    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        // Délégation au service pour mettre à jour l'état de la commande fournisseur
        return commandeFournisseurService.updateEtatCommande(idCommande, etatCommande);
    }

    // Implémentation de la méthode de mise à jour de la quantité d'une ligne de commande fournisseur
    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        // Délégation au service pour mettre à jour la quantité d'une ligne de commande fournisseur
        return commandeFournisseurService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    // Implémentation de la méthode de mise à jour du fournisseur d'une commande
    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        // Délégation au service pour mettre à jour le fournisseur associé à une commande
        return commandeFournisseurService.updateFournisseur(idCommande, idFournisseur);
    }

    // Implémentation de la méthode de mise à jour de l'article d'une ligne de commande fournisseur
    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        // Délégation au service pour mettre à jour l'article d'une ligne de commande fournisseur
        return commandeFournisseurService.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    // Implémentation de la méthode de suppression d'article d'une commande fournisseur
    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        // Délégation au service pour supprimer un article d'une commande fournisseur
        return commandeFournisseurService.deleteArticle(idCommande, idLigneCommande);
    }

    // Implémentation de la méthode de recherche de commande fournisseur par ID
    @Override
    public CommandeFournisseurDto findById(Integer id) {
        // Délégation au service pour trouver une commande fournisseur par son ID
        return commandeFournisseurService.findById(id);
    }

    // Implémentation de la méthode de recherche de commande fournisseur par code
    @Override
    public CommandeFournisseurDto findByCode(String code) {
        // Délégation au service pour trouver une commande fournisseur par son code
        return commandeFournisseurService.findByCode(code);
    }

    // Implémentation de la méthode pour récupérer toutes les commandes fournisseurs
    @Override
    public List<CommandeFournisseurDto> findAll() {
        // Délégation au service pour récupérer toutes les commandes fournisseurs
        return commandeFournisseurService.findAll();
    }

    // Implémentation de la méthode pour récupérer toutes les lignes de commande d'une commande fournisseur
    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande) {
        // Délégation au service pour récupérer les lignes de commande d'une commande fournisseur spécifique
        return commandeFournisseurService.findAllLignesCommandesFournisseurByCommandeFournisseurId(idCommande);
    }

    // Implémentation de la méthode de suppression de commande fournisseur
    @Override
    public void delete(Integer id) {
        // Délégation au service pour supprimer une commande fournisseur
        commandeFournisseurService.delete(id);
    }
}
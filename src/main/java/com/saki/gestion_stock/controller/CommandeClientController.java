// Déclaration du package pour les contrôleurs
package com.saki.gestion_stock.controller;

// Importations des classes nécessaires
import java.math.BigDecimal;
import java.util.List;

// Importations de l'interface API et des DTOs
import com.saki.gestion_stock.controller.api.CommandeClientApi;
import com.saki.gestion_stock.dto.CommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.model.EtatCommande;
// Importation du service CommandeClient
import com.saki.gestion_stock.services.CommandeClientService;
// Importations des classes Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation indiquant que cette classe est un contrôleur REST
@RestController
public class CommandeClientController implements CommandeClientApi {

    // Déclaration du service CommandeClient
    private CommandeClientService commandeClientService;

    // Injection de dépendance via le constructeur
    @Autowired
    public CommandeClientController(CommandeClientService commandeClientService) {
        this.commandeClientService = commandeClientService;
    }

    // Implémentation de la méthode de sauvegarde de commande client
    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {
        // Délégation au service pour sauvegarder la commande client avec encapsulation dans ResponseEntity
        return ResponseEntity.ok(commandeClientService.save(dto));
    }

    // Implémentation de la méthode de mise à jour de l'état de commande
    @Override
    public ResponseEntity<CommandeClientDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        // Délégation au service pour mettre à jour l'état de la commande
        return ResponseEntity.ok(commandeClientService.updateEtatCommande(idCommande, etatCommande));
    }

    // Implémentation de la méthode de mise à jour de la quantité d'une ligne de commande
    @Override
    public ResponseEntity<CommandeClientDto> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        // Délégation au service pour mettre à jour la quantité d'une ligne de commande
        return ResponseEntity.ok(commandeClientService.updateQuantiteCommande(idCommande, idLigneCommande, quantite));
    }

    // Implémentation de la méthode de mise à jour du client d'une commande
    @Override
    public ResponseEntity<CommandeClientDto> updateClient(Integer idCommande, Integer idClient) {
        // Délégation au service pour mettre à jour le client associé à une commande
        return ResponseEntity.ok(commandeClientService.updateClient(idCommande, idClient));
    }

    // Implémentation de la méthode de mise à jour de l'article d'une ligne de commande
    @Override
    public ResponseEntity<CommandeClientDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        // Délégation au service pour mettre à jour l'article d'une ligne de commande
        return ResponseEntity.ok(commandeClientService.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    // Implémentation de la méthode de suppression d'article d'une commande
    @Override
    public ResponseEntity<CommandeClientDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        // Délégation au service pour supprimer un article d'une commande
        return ResponseEntity.ok(commandeClientService.deleteArticle(idCommande, idLigneCommande));
    }

    // Implémentation de la méthode de recherche de commande client par ID
    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer id) {
        // Délégation au service pour trouver une commande client par son ID
        return ResponseEntity.ok(commandeClientService.findById(id));
    }

    // Implémentation de la méthode de recherche de commande client par code
    @Override
    public ResponseEntity<CommandeClientDto> findByCode(String code) {
        // Délégation au service pour trouver une commande client par son code
        return ResponseEntity.ok(commandeClientService.findByCode(code));
    }

    // Implémentation de la méthode pour récupérer toutes les commandes clients
    @Override
    public ResponseEntity<List<CommandeClientDto>> findAll() {
        // Délégation au service pour récupérer toutes les commandes clients
        return ResponseEntity.ok(commandeClientService.findAll());
    }

    // Implémentation de la méthode pour récupérer toutes les lignes de commande d'une commande client
    @Override
    public ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        // Délégation au service pour récupérer les lignes de commande d'une commande spécifique
        return ResponseEntity.ok(commandeClientService.findAllLignesCommandesClientByCommandeClientId(idCommande));
    }

    // Implémentation de la méthode de suppression de commande client
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        // Délégation au service pour supprimer une commande client
        commandeClientService.delete(id);
        // Retour d'une réponse OK sans contenu
        return ResponseEntity.ok().build();
    }
}
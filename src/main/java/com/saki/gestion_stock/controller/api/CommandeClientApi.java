// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importations des DTOs et modèles
import com.saki.gestion_stock.dto.CommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.model.EtatCommande;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
// Importations des classes Spring
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour la racine de l'application
import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

// Annotation Swagger pour documenter cette interface comme API "commandesclients"
@Api("commandesclients")
public interface CommandeClientApi {

    // Endpoint pour créer une commande client
    @PostMapping(APP_ROOT + "/commandesclients/create")
    // Méthode pour sauvegarder une nouvelle commande client
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    // Endpoint pour mettre à jour l'état d'une commande client
    @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
    // Méthode pour modifier l'état d'une commande (ex: EN_PREPARATION, LIVREE, etc.)
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    // Endpoint pour mettre à jour la quantité d'une ligne de commande
    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    // Méthode pour modifier la quantité d'un article dans une ligne de commande
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    // Endpoint pour mettre à jour le client d'une commande
    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")
    // Méthode pour modifier le client associé à une commande
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    // Endpoint pour mettre à jour l'article d'une ligne de commande
    @PatchMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    // Méthode pour modifier l'article d'une ligne de commande spécifique
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande") Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    // Endpoint pour supprimer un article d'une commande
    @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{idCommande}/{idLigneCommande}")
    // Méthode pour supprimer une ligne d'article d'une commande
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    // Endpoint pour rechercher une commande client par son ID
    @GetMapping(APP_ROOT + "/commandesclients/{idCommandeClient}")
    // Méthode pour trouver une commande client par son identifiant
    ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeClient);

    // Endpoint pour rechercher une commande client par son code
    @GetMapping(APP_ROOT + "/commandesclients/filter/{codeCommandeClient}")
    // Méthode pour trouver une commande client par son code
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    // Endpoint pour récupérer toutes les commandes clients
    @GetMapping(APP_ROOT + "/commandesclients/all")
    // Méthode pour récupérer la liste de toutes les commandes clients
    ResponseEntity<List<CommandeClientDto>> findAll();

    // Endpoint pour récupérer toutes les lignes de commande d'une commande client spécifique
    @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
    // Méthode pour trouver toutes les lignes de commande associées à une commande client
    ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

    // Endpoint pour supprimer une commande client
    @DeleteMapping(APP_ROOT + "/commandesclients/delete/{idCommandeClient}")
    // Méthode pour supprimer une commande client par son identifiant
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);

}
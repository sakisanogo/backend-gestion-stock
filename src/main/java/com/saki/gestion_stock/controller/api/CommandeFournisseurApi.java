// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importations des DTOs et modèles
import com.saki.gestion_stock.dto.CommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.model.EtatCommande;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
// Importations des annotations Spring Web
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de toutes les constantes pour les endpoints
import static com.saki.gestion_stock.utils.Constants.*;

// Annotation Swagger pour documenter cette interface comme API "commandefournisseur"
@Api("commandefournisseur")
public interface CommandeFournisseurApi {

    // Endpoint pour créer une commande fournisseur
    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    // Méthode pour sauvegarder une nouvelle commande fournisseur
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    // Endpoint pour mettre à jour l'état d'une commande fournisseur
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    // Méthode pour modifier l'état d'une commande fournisseur (ex: EN_PREPARATION, LIVREE, etc.)
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    // Endpoint pour mettre à jour la quantité d'une ligne de commande fournisseur
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    // Méthode pour modifier la quantité d'un article dans une ligne de commande fournisseur
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                  @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    // Endpoint pour mettre à jour le fournisseur d'une commande
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/fournisseur/{idCommande}/{idFournisseur}")
    // Méthode pour modifier le fournisseur associé à une commande
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    // Endpoint pour mettre à jour l'article d'une ligne de commande fournisseur
    @PatchMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    // Méthode pour modifier l'article d'une ligne de commande fournisseur spécifique
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande") Integer idCommande,
                                         @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    // Endpoint pour supprimer un article d'une commande fournisseur
    @DeleteMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
    // Méthode pour supprimer une ligne d'article d'une commande fournisseur
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    // Endpoint pour rechercher une commande fournisseur par son ID
    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT)
    // Méthode pour trouver une commande fournisseur par son identifiant
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    // Endpoint pour rechercher une commande fournisseur par son code
    @GetMapping(FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT)
    // Méthode pour trouver une commande fournisseur par son code
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    // Endpoint pour récupérer toutes les commandes fournisseurs
    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT)
    // Méthode pour récupérer la liste de toutes les commandes fournisseurs
    List<CommandeFournisseurDto> findAll();

    // Endpoint pour récupérer toutes les lignes de commande d'une commande fournisseur spécifique
    @GetMapping(COMMANDE_FOURNISSEUR_ENDPOINT + "/lignesCommande/{idCommande}")
    // Méthode pour trouver toutes les lignes de commande associées à une commande fournisseur
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

    // Endpoint pour supprimer une commande fournisseur
    @DeleteMapping(DELETE_COMMANDE_FOURNISSEUR_ENDPOINT)
    // Méthode pour supprimer une commande fournisseur par son identifiant
    void delete(@PathVariable("idCommandeFournisseur") Integer id);

}
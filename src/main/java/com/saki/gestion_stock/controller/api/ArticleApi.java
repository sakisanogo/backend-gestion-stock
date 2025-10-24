// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importations des DTOs et annotations Swagger
import com.saki.gestion_stock.dto.ArticleDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour la racine de l'application
import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

// Annotation Swagger pour documenter cette interface comme API "articles"
@Api("articles")
public interface ArticleApi {

    // Endpoint pour créer ou modifier un article
    @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // Documentation Swagger pour l'opération
    @ApiOperation(value = "Enregistrer un article", notes = "Cette methode permet d'enregistrer ou modifier un article", response = ArticleDto.class)
    // Documentation des réponses possibles
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet article cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
    // Méthode pour sauvegarder un article (create ou update)
    ArticleDto save(@RequestBody ArticleDto dto);

    // Endpoint pour rechercher un article par son ID
    @GetMapping(value = APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet de chercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    // Méthode pour trouver un article par son identifiant
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    // Endpoint pour rechercher un article par son code
    @GetMapping(value = APP_ROOT + "/articles/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet de chercher un article par son CODE", response =
            ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    // Méthode pour trouver un article par son code
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    // Endpoint pour récupérer tous les articles
    @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existent "
            + "dans la BDD", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    // Méthode pour récupérer la liste de tous les articles
    List<ArticleDto> findAll();

    // Endpoint pour récupérer l'historique des ventes d'un article
    @GetMapping(value = APP_ROOT + "/articles/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour trouver l'historique des ventes d'un article spécifique
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    // Endpoint pour récupérer l'historique des commandes clients d'un article
    @GetMapping(value = APP_ROOT + "/articles/historique/commandeclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour trouver l'historique des commandes clients d'un article (note: légère faute de frappe dans le nom)
    List<LigneCommandeClientDto> findHistoriaueCommandeClient(@PathVariable("idArticle") Integer idArticle);

    // Endpoint pour récupérer l'historique des commandes fournisseurs d'un article
    @GetMapping(value = APP_ROOT + "/articles/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour trouver l'historique des commandes fournisseurs d'un article
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

    // Endpoint pour récupérer tous les articles d'une catégorie spécifique
    @GetMapping(value = APP_ROOT + "/articles/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour trouver tous les articles appartenant à une catégorie
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idCategory);

    // Endpoint pour supprimer un article
    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a ete supprime")
    })
    // Méthode pour supprimer un article par son identifiant
    void delete(@PathVariable("idArticle") Integer id);

}
// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation du DTO Category
import com.saki.gestion_stock.dto.CategoryDto;
// Importations des annotations Swagger
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
// Importations des classes Spring
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour la racine de l'application
import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

// Annotation Swagger pour documenter cette interface comme API "categories"
@Api("categories")
public interface CategoryApi {

    // Endpoint pour créer ou modifier une catégorie
    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // Documentation Swagger pour l'opération de sauvegarde
    @ApiOperation(value = "Enregistrer une categorie", notes = "Cette methode permet d'enregistrer ou modifier une categorie", response =
            CategoryDto.class)
    // Documentation des réponses possibles
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet category cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet category n'est pas valide")
    })
    // Méthode pour sauvegarder une catégorie (create ou update)
    CategoryDto save(@RequestBody CategoryDto dto);

    // Endpoint pour rechercher une catégorie par son ID
    @GetMapping(value = APP_ROOT + "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par ID", notes = "Cette methode permet de chercher une categorie par son ID", response =
            CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune categorie n'existe dans la BDD avec l'ID fourni")
    })
    // Méthode pour trouver une catégorie par son identifiant
    CategoryDto findById(@PathVariable("idCategory") Integer idCategory);

    // Endpoint pour rechercher une catégorie par son code
    @GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par CODE", notes = "Cette methode permet de chercher une categorie par son CODE", response =
            CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    // Méthode pour trouver une catégorie par son code
    CategoryDto findByCode(@PathVariable("codeCategory") String codeCategory);

    // Endpoint pour récupérer toutes les catégories
    @GetMapping(value = APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des categories", notes = "Cette methode permet de chercher et renvoyer la liste des categories qui existent "
            + "dans la BDD", responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    // Méthode pour récupérer la liste de toutes les catégories
    List<CategoryDto> findAll();

    // Endpoint pour supprimer une catégorie
    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer une categorie par ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a ete supprime")
    })
    // Méthode pour supprimer une catégorie par son identifiant
    void delete(@PathVariable("idCategory") Integer id);

}
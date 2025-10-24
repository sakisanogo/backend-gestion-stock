// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation du DTO Mouvement de Stock
import com.saki.gestion_stock.dto.MvtStkDto;
// Importations des annotations Swagger
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import java.util.List;
// Importations des classes Spring
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// Importation de la constante pour la racine de l'application
import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

// Annotation Swagger pour documenter cette interface comme API "mvtstk" (mouvements de stock)
@Api("mvtstk")
public interface MvtStkApi {

    // Endpoint pour récupérer le stock réel d'un article
    @ApiOperation(value = "Récupérer le stock réel d'un article", response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock récupéré avec succès"),
            @ApiResponse(code = 404, message = "Article non trouvé")
    })
    @GetMapping(APP_ROOT + "/mvtstk/stockreel/{idArticle}")
    // Méthode pour calculer et retourner le stock réel d'un article
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    // Endpoint pour récupérer l'historique des mouvements de stock d'un article
    @ApiOperation(value = "Liste des mouvements de stock d'un article", response = MvtStkDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Mouvements récupérés avec succès"),
            @ApiResponse(code = 404, message = "Article non trouvé")
    })
    @GetMapping(APP_ROOT + "/mvtstk/filter/article/{idArticle}")
    // Méthode pour récupérer tous les mouvements de stock d'un article spécifique
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    // Endpoint pour enregistrer une entrée de stock (ajout de stock)
    @ApiOperation(value = "Enregistrer une entrée de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entrée de stock enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/entree",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour enregistrer un mouvement d'entrée de stock (réception)
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    // Endpoint pour enregistrer une sortie de stock (retrait de stock)
    @ApiOperation(value = "Enregistrer une sortie de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sortie de stock enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides"),
            @ApiResponse(code = 409, message = "Stock insuffisant")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/sortie",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour enregistrer un mouvement de sortie de stock (vente, utilisation)
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    // Endpoint pour enregistrer une correction positive de stock (ajustement positif)
    @ApiOperation(value = "Correction positive de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correction positive enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/correctionpos",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour enregistrer une correction positive (ajout de stock pour correction)
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    // Endpoint pour enregistrer une correction négative de stock (ajustement négatif)
    @ApiOperation(value = "Correction négative de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correction négative enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides"),
            @ApiResponse(code = 409, message = "Stock insuffisant")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/correctionneg",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // Méthode pour enregistrer une correction négative (retrait de stock pour correction)
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);
}
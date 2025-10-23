package com.saki.gestion_stock.controller.api;

import com.saki.gestion_stock.dto.MvtStkDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.saki.gestion_stock.utils.Constants.APP_ROOT;

@Api("mvtstk")
public interface MvtStkApi {

    @ApiOperation(value = "Récupérer le stock réel d'un article", response = BigDecimal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Stock récupéré avec succès"),
            @ApiResponse(code = 404, message = "Article non trouvé")
    })
    @GetMapping(APP_ROOT + "/mvtstk/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @ApiOperation(value = "Liste des mouvements de stock d'un article", response = MvtStkDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Mouvements récupérés avec succès"),
            @ApiResponse(code = 404, message = "Article non trouvé")
    })
    @GetMapping(APP_ROOT + "/mvtstk/filter/article/{idArticle}")
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @ApiOperation(value = "Enregistrer une entrée de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entrée de stock enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/entree",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @ApiOperation(value = "Enregistrer une sortie de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sortie de stock enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides"),
            @ApiResponse(code = 409, message = "Stock insuffisant")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/sortie",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @ApiOperation(value = "Correction positive de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correction positive enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/correctionpos",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    @ApiOperation(value = "Correction négative de stock", response = MvtStkDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correction négative enregistrée avec succès"),
            @ApiResponse(code = 400, message = "Données invalides"),
            @ApiResponse(code = 409, message = "Stock insuffisant")
    })
    @PostMapping(value = APP_ROOT + "/mvtstk/correctionneg",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);
}
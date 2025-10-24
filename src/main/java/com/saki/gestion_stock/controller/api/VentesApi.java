// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation du DTO Ventes
import com.saki.gestion_stock.dto.VentesDto;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.util.List;
// Importations des annotations Spring Web
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Importation de la constante pour l'endpoint ventes
import static com.saki.gestion_stock.utils.Constants.VENTES_ENDPOINT;

// Annotation Swagger pour documenter cette interface comme API "ventes"
@Api("ventes")
public interface VentesApi {

    // Endpoint pour créer ou modifier une vente
    @PostMapping(VENTES_ENDPOINT + "/create")
    // Méthode pour sauvegarder une vente (create ou update)
    VentesDto save(@RequestBody VentesDto dto);

    // Endpoint pour rechercher une vente par son ID
    @GetMapping(VENTES_ENDPOINT + "/{idVente}")
    // Méthode pour trouver une vente par son identifiant
    VentesDto findById(@PathVariable("idVente") Integer id);

    // Endpoint pour rechercher une vente par son code
    @GetMapping(VENTES_ENDPOINT + "/filter/{codeVente}") // ← CHANGEMENT ICI
    // Méthode pour trouver une vente par son code
    VentesDto findByCode(@PathVariable("codeVente") String code);

    // Endpoint pour récupérer toutes les ventes
    @GetMapping(VENTES_ENDPOINT + "/all")
    // Méthode pour récupérer la liste de toutes les ventes
    List<VentesDto> findAll();

    // Endpoint pour supprimer une vente
    @DeleteMapping(VENTES_ENDPOINT + "/delete/{idVente}")
    // Méthode pour supprimer une vente par son identifiant
    void delete(@PathVariable("idVente") Integer id);

}
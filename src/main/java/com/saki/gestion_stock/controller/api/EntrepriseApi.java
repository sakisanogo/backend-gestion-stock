// Déclaration du package pour les interfaces API
package com.saki.gestion_stock.controller.api;

// Importation du DTO Entreprise
import com.saki.gestion_stock.dto.EntrepriseDto;
// Importation de l'annotation Swagger
import io.swagger.annotations.Api;
import java.util.List;
// Importations des annotations Spring Web
import org.springframework.web.bind.annotation.*;

// Importation de la constante pour l'endpoint entreprise
import static com.saki.gestion_stock.utils.Constants.ENTREPRISE_ENDPOINT;

// Configuration CORS pour autoriser les requêtes depuis le frontend Angular
@CrossOrigin(origins = "http://localhost:4200")
// Annotation Swagger pour documenter cette interface comme API "entreprises"
@Api("entreprises")
public interface EntrepriseApi {

    // Endpoint pour créer une entreprise
    @PostMapping(ENTREPRISE_ENDPOINT + "/create")
    // Méthode pour sauvegarder une nouvelle entreprise
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    // Endpoint pour rechercher une entreprise par son ID
    @GetMapping(ENTREPRISE_ENDPOINT + "/{idEntreprise}")
    // Méthode pour trouver une entreprise par son identifiant
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    // Endpoint pour récupérer toutes les entreprises
    @GetMapping(ENTREPRISE_ENDPOINT + "/all")
    // Méthode pour récupérer la liste de toutes les entreprises
    List<EntrepriseDto> findAll();

    // Endpoint pour supprimer une entreprise
    @DeleteMapping(ENTREPRISE_ENDPOINT + "/delete/{idEntreprise}")
    // Méthode pour supprimer une entreprise par son identifiant
    void delete(@PathVariable("idEntreprise") Integer id);

}